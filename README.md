# 📱アプリ名
## Create-Memo-App 

# 💡概要
メモした内容をDBに保存出来ます。<br>
保存したメモを単語で検索したり、日付でフィルターをかけ過去にメモした内容を見返したり出来るアプリです。
ユーザを登録し、ログイン機能をつけることでログインしたユーザのメモ情報だけを表示することが出来ます。

# 🌟制作背景(意図)
プログラミング等の学習をする上で必要なことをメモする際、スマホにメモを取るときがあるのですが、私が使用していたアプリは単純なメモ帳アプリで単語の検索や保存した日でフィルターを掛けられないものを使用しておりました。
そこで、メモした内容を単語で保存したり日付でフィルターを掛けたり出来るアプリを自分で作成してみようと思い、今回のアプリを作成しました。
また、Spring Frameworkを学習したので知識のアウトプットをしたいと思いこちらのアプリを作成しました。

# 📝使い方

### トップページ（ログイン時）
<img width="1082" alt="ログイントップページ" src="https://github.com/gomachan3232/Create-Memo-App/assets/75553558/0e781433-3e79-432a-ad4b-e22b7ab8b0e5">
- メモを入力、保存することが出来ます
- 保存したメモを単語で検索することが出来ます
- 保存したメモを登録日時で検索することが出来ます
- 情報変更ボタンでアカウント情報変更画面に遷移します
- ログアウトボタンでログアウト出来ます

### トップページ（ログアウト時）
##### ＜ユーザー検索画面＞
<img width="1079" alt="ログアウトトップページ" src="https://github.com/gomachan3232/Create-Memo-App/assets/75553558/3b52da07-23fb-48c7-99a7-b82b547f4cd2">
- 新規ボタンでアカウント登録画面に遷移します
- ログインボタンでログイン画面に遷移します

### アカウント登録画面
<img width="1082" alt="アカウント新規登録ページ" src="https://github.com/gomachan3232/Create-Memo-App/assets/75553558/cb73081f-1158-4c37-b6e3-34106d0d3d05">
- ユーザ名（変更不可）、メールアドレス、パスワードを入力することでアカウント登録が出来ます

### ログイン画面
<img width="1084" alt="ログインページ" src="https://github.com/gomachan3232/Create-Memo-App/assets/75553558/8cbe3a93-82b5-4ac4-be37-f498c590083f">
- ユーザ名とパスワードを入力し、ログインが出来ます

### アカウント情報変更画面
<img width="1080" alt="アカウント情報変更ページ" src="https://github.com/gomachan3232/Create-Memo-App/assets/75553558/303a5fd4-e0cf-4911-884b-feb0e4c3f780">
- メールアドレスとパスワードを変更することが出来ます

### メモ編集、削除画面
<img width="1082" alt="メモ編集ページ" src="https://github.com/gomachan3232/Create-Memo-App/assets/75553558/973f1b1a-d0f6-4ab8-b6ea-387cc222c4de">
- 作成したメモを編集することが出来ます
- 作成したメモを削除することが出来ます

# ✨工夫したポイント(頑張ったところ)
DBからログインユーザのメモだけを抜き出し、
登録日時順にソートする仕様にすることが大変でしたが実装することが出来ました。
(MemoSpecification.javaとMemoService.java)
あと、Spring Securityを使ったログイン機能の実装も頑張りました。
(SecurityConfig.java)
処理の方法(どのように画面が遷移されるのか等)が始めは理解できませんでしたが、
色々調査することでなぜそのように動作するのか理解できるようになりました。

# 💬使用した技術(開発環境)
## バックエンド
Java, Spring Framework
## フロントエンド
HTML, CSS
## データベース
H2 Database
## ソース管理
GitHub
## テスト
JUnit
## エディタ
Eclipse

#  🚩課題や今後実装したい機能
- Bootstrapを使った画面作成
- 非同期通信によるデータの絞り込み
- メモをワンクリックでコピー出来る機能

# 📁DB設計
## Accountテーブル
| Column   | Type   | Options                          |
| -------- | ------ | -------------------------------- |
| username | string | null: false, unique: true        |
| email    | string | null: false, email_format: true  |
| password | string | null: false                      |
### Association
- has_many :memos

## Memoテーブル
| Column      | Type          | Options |
| ----------- | ------------- | ------- |
| id          | long          |         |
| username    | string        |         |
| memoDate    | localdatetime |         |
| memoContent | string        |         |

### Association
- belongs_to: account
