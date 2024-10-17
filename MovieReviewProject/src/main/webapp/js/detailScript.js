function scrollLeft() {
    const container = document.querySelector('.cast-list');
    container.scrollBy({
        left: -150, // 스크롤할 거리 조정
        behavior: 'smooth'
    });
}

function scrollRight() {
    const container = document.querySelector('.cast-list');
    container.scrollBy({
        left: 150, // 스크롤할 거리 조정
        behavior: 'smooth'
    });
}
