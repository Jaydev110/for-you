const noBtn = document.getElementById('noBtn');
const yesBtn = document.getElementById('yesBtn');
const mainBox = document.getElementById('main-box');
const successMsg = document.getElementById('success-msg');
const audio = document.getElementById('bgMusic');
const sendBtn = document.getElementById('sendBtn');
const replyBox = document.getElementById('replyBox');

// 1. Typing Effect
const text = "Hey Beautiful, I have a question...";
let index = 0;
function type() {
    if (index < text.length) {
        document.getElementById("typing-text").innerHTML += text.charAt(index);
        index++;
        setTimeout(type, 100);
    }
}
window.onload = type;

// 2. Play Music on First Interaction
document.addEventListener('click', () => {
    audio.play().catch(e => console.log("Audio play blocked until interaction."));
}, { once: true });

// 3. The Running 'No' Button Logic
noBtn.addEventListener('mouseover', () => {
    const maxX = window.innerWidth - noBtn.offsetWidth;
    const maxY = window.innerHeight - noBtn.offsetHeight;
    
    const randomX = Math.floor(Math.random() * maxX);
    const randomY = Math.floor(Math.random() * maxY);
    
    noBtn.style.left = `${randomX}px`;
    noBtn.style.top = `${randomY}px`;
});

// 4. 'Yes' Button Celebration
yesBtn.addEventListener('click', () => {
    mainBox.classList.add('hidden');
    successMsg.classList.remove('hidden');
    
    // Confetti Blast
    confetti({
        particleCount: 150,
        spread: 70,
        origin: { y: 0.6 }
    });
});

// 5. Send Reply to WhatsApp
sendBtn.addEventListener('click', () => {
    const reply = replyBox.value;
    if (reply.trim() !== "") {
        // REPLACE 911234567890 with your actual phone number
        const phoneNumber = "919776830020"; 
        const whatsappUrl = `https://wa.me/${phoneNumber}?text=I saw your surprise! 😍 My reply: ${encodeURIComponent(reply)}`;
        window.open(whatsappUrl, '_blank');
    } else {
        alert("Please write a small reply first! ❤️");
    }
});