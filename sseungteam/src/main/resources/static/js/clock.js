const watchClock = () => {
    function leftPad(number) {
        if (number < 10) {
            return `0${number}`;
        }

        return `${number}`;
    };

    function setText(selector, text) {
        const targetElement = document.querySelector(selector);

        if (!targetElement) {
            return;
        }

        targetElement.innerText = text;
    };

    function getDayText(index) {
        const days = ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'];
        return days[index];
    };

    const present = new Date();


    const hour = leftPad(present.getHours());
    const minute = leftPad(present.getMinutes());
    const seconds = leftPad(present.getSeconds());

    setText('#hours', hour);
    setText('#minute', minute);
    setText('#seconds', seconds);

    const year = present.getFullYear();
    const month = leftPad(present.getMonth() + 1);
    const date = leftPad(present.getDate());
    const day = getDayText(present.getDay());

    setText('#year', year);
    setText('#month', month);
    setText('#date', date);
    setText('#day', day);
};

watchClock();

const clockInterval = setInterval(watchClock, 1000);