# 🦝 너굴맵(Nugulmap) Android 개발 가이드

이 문서는 너굴맵 안드로이드 앱 개발을 위한 팀 온보딩 및 가이드라인입니다.

## 1. 🛠 프로젝트 기술 스택 (권장)
*   **Language:** Kotlin
*   **UI Framework:** Jetpack Compose (Material Design 3)
*   **Architecture:** MVVM + Clean Architecture
*   **Network:** Retrofit2 + OkHttp3
*   **DI (Dependency Injection):** Hilt
*   **Async:** Coroutines + Flow
*   **Map SDK:** KakaoMap Android SDK
*   **Image Loading:** Coil

---

## 2. 👥 업무 분담 (2인 체제 제안)

### 🧑‍💻 개발자 A: 지도 & 코어 (Map & Core)
*   **핵심 역할:** 지도 SDK 연동 및 위치 기반 서비스 구현
*   **주요 태스크:**
    *   KakaoMap SDK 초기 설정 및 API 키 관리
    *   사용자 위치 추적 (GPS 권한, 나침반 모드)
    *   흡연 구역(Zone) 마커 렌더링 및 클러스터링(Clustering)
    *   지도 오버레이 UI (마커 클릭 시 말풍선 등)
    *   외부 지도 앱(카카오맵/네이버지도) 길찾기 연동

### 🧑‍💻 개발자 B: 사용자 경험 & 데이터 (UI & Data)
*   **핵심 역할:** 사용자 인증, 데이터 흐름, 화면 UI 구성
*   **주요 태스크:**
    *   전체 앱 디자인 시스템 구축 (Color, Typography, Components)
    *   OAuth2 로그인 (카카오/구글) 및 토큰 관리 (DataStore)
    *   API 통신 모듈 (Retrofit) 설계 및 Repository 패턴 구현
    *   흡연 구역 제보 화면 (이미지 업로드 포함)
    *   마이페이지, 검색 화면, 공지사항 등 일반 UI 구현

---

## 3. 🔌 백엔드 연결 가이드

### API 명세서
*   백엔드 레포지토리의 `backend/api_specification.md` 파일을 참고하세요.
*   주요 리소스: `/users` (사용자), `/zones` (흡연구역), `/images` (이미지)

### Base URL 설정
로컬 개발 환경(Emulator vs Real Device)에 따라 Base URL이 달라집니다.

**1. 안드로이드 에뮬레이터 (Emulator) 사용 시**
*   로컬 서버(`localhost:8080`)는 에뮬레이터 입장에서 `10.0.2.2`입니다.
*   **Base URL:** `http://10.0.2.2:8080`

**2. 실물 기기 (Real Device) 사용 시**
*   PC와 휴대폰이 **동일한 와이파이**에 접속해야 합니다.
*   PC의 사설 IP 주소를 확인하세요 (예: `ipconfig` 또는 `ifconfig` -> `192.168.0.x`).
*   **Base URL:** `http://192.168.0.x:8080`

**3. `network_security_config.xml` 설정 (필수)**
*   개발 중 `http` (평문 통신) 허용을 위해 `res/xml/network_security_config.xml` 설정이 필요합니다.
```xml
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">10.0.2.2</domain>
        <domain includeSubdomains="true">192.168.0.10</domain> <!-- 본인 IP -->
    </domain-config>
</network-security-config>
```

---

## 4. 🔑 주요 키 관리 (Secrets)
보안을 위해 API 키는 `local.properties`에 저장하고 `BuildConfig`로 불러와야 합니다. **절대 Git에 커밋하지 마세요.**

**local.properties 예시:**
```properties
KAKAO_NATIVE_APP_KEY=your_kakao_native_key_here
BASE_URL="http://10.0.2.2:8080"
```

---

## 5. 🚀 시작하기 (Getting Started)
1. 이 레포지토리를 클론합니다.
2. `local.properties` 파일을 생성하고 위 키 정보를 입력합니다.
3. Android Studio에서 프로젝트를 엽니다 (Gradle Sync).
4. 백엔드 서버를 실행 상태로 둡니다.
5. `app` 모듈을 실행합니다.
