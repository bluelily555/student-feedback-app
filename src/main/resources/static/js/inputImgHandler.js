// 이미지 첨부파일 수정 핸들러
function imageInputOnChange(event) {
    fileTypeCheck(event);
    setThumbnail(event);
}

const fileTypes = [
    "image/apng",
    "image/bmp",
    "image/gif",
    "image/jpeg",
    "image/pjpeg",
    "image/png",
    "image/svg+xml",
    "image/tiff",
    "image/webp",
    "image/x-icon"
];

// 이미지 업로드 확장자 더블 체크
function fileTypeCheck(event) {
    let hasWrongFile = false;
    const dataTransfer = new DataTransfer();

    for (let file of event.target.files) {
        if (fileTypes.includes(file.type)) {
            dataTransfer.items.add(file);
        } else {
            hasWrongFile = true;
        }
    }

    if (hasWrongFile) alert('이미지 파일만 업로드할 수 있습니다.')

    event.target.files = dataTransfer.files;
}

// 썸네일 설정
function setThumbnail(event) {
    const preview = document.querySelector(".thumbnails");

    while (preview.firstChild) {
        preview.removeChild(preview.firstChild);
    }

    for (let i = 0; i < event.target.files.length; i++) {
        const image = event.target.files[i]
        const col = document.createElement('div');
        const card = document.createElement('div')
        const img = document.createElement('img');
        const label = document.createElement('div');
        const deleteBtn = document.createElement('i');

        col.className = 'col';

        card.className = 'card m-2 position-relative';

        img.className = 'card-img-top';
        img.setAttribute('src', URL.createObjectURL(image));

        label.className = 'card-footer h6 text-center text-nowrap overflow-hidden mt-auto';
        label.innerText = image.name;

        deleteBtn.className = 'fa-solid fa-circle-minus text-danger fs-5 mt-2 me-2 position-absolute top-0 end-0';
        deleteBtn.setAttribute('role', 'button');
        deleteBtn.addEventListener('click', () => {
            deleteFile('images-input', i)
            imageInput.dispatchEvent(new Event('change'));
        });

        card.appendChild(img);
        card.appendChild(label);
        card.appendChild(deleteBtn);

        col.appendChild(card);

        document.querySelector(".thumbnails").appendChild(col);
    }
}

function deleteFile(inputFileId, index) {
    const dataTransfer = new DataTransfer();
    const input = document.getElementById(inputFileId);
    const files = input.files;
    let fileArray = Array.from(files);

    fileArray.splice(index, 1);

    fileArray.forEach(file => dataTransfer.items.add(file));

    input.files = dataTransfer.files;
}