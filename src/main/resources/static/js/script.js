//TOPページの要素を取得（要素は複数あるため、querySelectorAllで取得）
const topPageItems = document.querySelectorAll("#top-page-item");

//取得した要素を繰り返し実行
for(let i = 0; i < topPageItems.length; i++) {
    const keyframes = {
        opacity: [0, 1],
        translate: ["0 50px", 0],
    };
    const options = {
        duration: 1500,
        delay: i * 500,
        fill: "forwards",
    };
    topPageItems[i].animate(keyframes, options);
}