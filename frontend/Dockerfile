# 빌드 단계
FROM node:18-alpine AS builder
WORKDIR /app

# 의존성 파일 복사 및 설치
COPY package.json package-lock.json ./
RUN npm ci

# 소스 코드 복사 및 빌드
COPY . .
RUN npm run build

# 실행 단계
FROM node:18-alpine AS runner
WORKDIR /app

ENV NODE_ENV=production
ENV PORT=3000
ENV HOSTNAME="0.0.0.0"

# 시스템 의존성 설치 (필요한 경우)
RUN addgroup -g 1001 -S nodejs
RUN adduser -S nextjs -u 1001

# 빌드된 파일 및 필요한 파일들만 복사
COPY --from=builder /app/next.config.mjs ./
COPY --from=builder /app/public ./public
COPY --from=builder /app/.next/standalone ./
COPY --from=builder /app/.next/static ./.next/static

# 사용자 변경
USER nextjs

# 포트 설정
EXPOSE 3000

# 애플리케이션 실행
CMD ["node", "server.js"]