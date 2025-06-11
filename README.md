#  Language News Validator 

A Selenium-powered Java automation tool that visits [Inshorts](https://inshorts.com/en/read), scrapes all visible headlines, checks if each is written in English, and logs the result with screenshot evidence.

---

##  Features

-  Validates if headlines are written in English
-  Takes individual screenshots for each headline
-  Creates timestamped log reports
-  Captures error screenshots if anything fails
-  Great showcase for Java + Selenium + Automation Testing skills

---

##  Tech Stack

| Tool/Library      | Use |
|------------------|-----|
| Java             | Core programming |
| Selenium         | Web automation |
| WebDriverManager | Auto-driver setup |
| LogUtils         | Custom logging |
| ScreenshotUtils  | Screenshot handling |
| IntelliJ/Eclipse | IDE |
| Maven            | Build tool (optional) |

---

##  Project Structure


LanguageNewsValidator/
│
├── src/
│ └── main/
│ └── java/
│ └── com/job/bot/
│ ├── NewsLanguageValidator.java
│ └── utils/
│ ├── LogUtils.java
│ └── ScreenshotUtils.java
│
├── reports/
│ └── log_YYYY-MM-DD.txt
│
├── screenshots/
│ └── EN_1_success.png
│ └── NOT_EN_2_success.png
│ └── Error_Screenshot.png
│
├── .gitignore
└── README.md




