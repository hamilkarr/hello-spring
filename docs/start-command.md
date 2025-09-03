 ## 파일 변경시 자동으로 컴파일
 .\gradlew.bat build -x test --continuous 
 - 실시간 파일 변화 감지후 빌드
 
 ./gradlew bootRun
 - devtools 의존성에 의해 파일 변경시 자동으로 재시작한다.
 
 - 결론: 파일변경하면 자동으로 다시 빌드하고, 자동으로 다시 시작한다.

 ## VS Code에서 내부 터미널로 동시에 실행 (권장)
 - Terminal → Run Task… → `dev` 선택 (또는 Ctrl+Shift+P → "Run Task" → `dev`)
 - 두 개의 내부 터미널 탭이 자동으로 생성됨:
   - `build-watch`: `gradlew.bat build -x test --continuous`
   - `boot-run`: `gradlew.bat bootRun`
 - 중지 방법: 각 터미널 탭에서 종료(휴지통 아이콘)