var goToPage = function(desiredPage){
    var numPages = PDFViewerApplication.pagesCount;
    if((desiredPage > numPages) || (desiredPage < 1)){
        return;
    }
    PDFViewerApplication.page = desiredPage;
};


var openFileFromBase64 = function(data) {
    var arr = base64ToArrayBuffer(data);
    console.log('loading...');
    PDFViewerApplication.open(arr).then(function() {
       console.log('loaded.');
    });
};

var base64ToArrayBuffer = function(base64) {
    var binary_string = window.atob(base64);
    var len = binary_string.length;
    var bytes = new Uint8Array(new ArrayBuffer(len));
    alert(len);
    for (var i = 0; i < len; i++)        {
      bytes[i] = binary_string.charCodeAt(i);
    }
    return bytes.buffer;
};