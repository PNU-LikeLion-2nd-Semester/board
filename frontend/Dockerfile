# 빌드 단계
FROM node:22.2.0 AS build

# 작업 디렉토리 설정
WORKDIR /app

# 의존성 파일 복사
COPY package.json yarn.lock ./

# 의존성 설치
RUN yarn install --frozen-lockfile

# 소스 코드 복사
COPY . .

# 프로덕션용 빌드
RUN yarn build

# 프로덕션 단계
FROM nginx:stable-alpine

# 빌드된 파일을 Nginx 서버로 복사
COPY --from=build /app/build /usr/share/nginx/html

# 80번 포트 노출
EXPOSE 80

# Nginx 실행
CMD ["nginx", "-g", "daemon off;"]