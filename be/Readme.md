## IntelliJ IDEA에서 Build and Run, Run Tests를 IntelliJ로 설정하는 방법

---

IntelliJ IDEA에서는 Gradle을 사용하여 빌드 및 테스트를 수행할 수 있지만, 더 빠른 피드백을 위해 IntelliJ 빌드 시스템을 사용하는 것이 유용할 때가 있습니다. 아래는 프로젝트 빌드와 테스트를
IntelliJ 방식으로 설정하는 방법입니다.

1. 상단 메뉴에서 **File > Settings** (macOS의 경우 **IntelliJ IDEA > Preferences**)로 이동합니다.
2. **Build, Execution, Deployment > Build Tools > Gradle** 메뉴로 이동합니다.
3. 화면에서 아래 두 가지 옵션을 **IntelliJ**로 설정합니다.

- **Build and run using**: `IntelliJ`
- **Run tests using**: `IntelliJ`

4. 설정을 변경한 후 **OK** 또는 **Apply** 버튼을 눌러 변경 사항을 저장합니다.

### 왜 IntelliJ로 설정하나요?

- **IntelliJ 빌드 시스템**은 프로젝트의 작은 변경 사항에 빠르게 반응하므로, 코드를 빠르게 빌드하고 실행하는 데 적합합니다.
- **테스트 실행 속도**도 IntelliJ를 사용할 경우 더 빠를 수 있습니다.
- IntelliJ 빌드 시스템은 Gradle보다 더 빠르게 빌드를 수행할 수 있습니다. 다만, Gradle이 제공하는 전체 빌드
  스크립트 기능을 사용하지 않으므로 특정 상황에서는 Gradle을 다시 사용하는 것이 필요할 수 있습니다.

## 코드 포매터 설정 방법 (코드를 깔끔하게)

이 프로젝트에서는 네이버 Java 코드 포매터를 따릅니다. 이를 IntelliJ에 자동으로 적용하도록 설정하는 방법은 다음과 같습니다.

### IntelliJ에 네이버 코드 포매터 설정하기

#### 네이버 코드 포매터 XML 다운로드

네이버 코드 포매터 XML
파일을 [다운로드](https://github.com/naver/hackday-conventions-java/blob/master/rule-config/naver-intellij-formatter.xml)합니다.

#### IntelliJ에 XML 파일 가져오기

1. IntelliJ를 열고, 상단 메뉴에서 `File > Settings` (또는 `Preferences` on macOS)를 클릭합니다.
2. `Editor > Code Style > Java`로 이동합니다.
3. 우측 상단의 톱니바퀴 버튼을 클릭하고, `Import Scheme > IntelliJ IDEA Code Style XML`을 선택합니다.
4. 다운로드한 `rule-config/naver-intellij-formatter.xml` 파일을 선택합니다.

#### 코드 스타일 설정 적용

1. 적용된 코드 스타일을 선택하고, `OK` 버튼을 클릭하여 설정을 저장합니다.

### 프로젝트에 코드 스타일 설정 파일 추가

프로젝트 내에서 모든 기여자들이 동일한 코드 스타일을 사용할 수 있도록 코드 스타일 설정 파일을 프로젝트에 포함시킵니다.

### 코드 저장 시 자동 포매팅 및 import 최적화 설정

1. IntelliJ를 열고, 상단 메뉴에서 `File > Settings` (또는 `Preferences` on macOS`)를 클릭합니다.
2. `Tools > Actions on Save`로 이동합니다.
3. "Reformat code" 및 "Optimize imports" 옵션을 선택합니다.
4. `OK` 버튼을 클릭하여 설정을 저장합니다.

이제 IntelliJ에서 파일을 저장할 때마다 자동으로 코드 포맷팅과 import 최적화가 수행됩니다.