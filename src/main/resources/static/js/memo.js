//memoページのメモ登録時の要素を取得
const memoText = document.querySelector("#memo-text");
//memoページのメモの文字数をカウントする要素を取得
const memoCount = document.querySelector("#memo-count");
//memoページのメモ登録ボタンの要素を取得
const memoBtn = document.querySelector("#memo-btn");

//memoページ表示時はメモ登録ボタンをdisableにする
memoBtn.disabled = true;

//キーボード入力を行った際にイベント発火
memoText.addEventListener("keyup", () => {
   memoCount.textContent = memoText.value.length;
   //メモの文字数が500文字より多い場合
   if(memoText.value.length > 500) {
        //redクラスを付与し、文字色を赤にする
        memoCount.classList.add("red");
        //メモ登録ボタンをdisableにする
        memoBtn.disabled = true;
    //メモの文字数が0文字の場合
    } else if(memoText.value.length == 0) {
        //メモ登録ボタンをdisableにする
        memoBtn.disabled = true;
    //それ以外
    } else {
        //500文字以下ならredクラスの付与を外す
        memoCount.classList.remove("red");
        //メモ登録ボタンをenableにする
        memoBtn.disabled = false;
    }
});