var Filters = {
    lastImageDataStack: [],
    applyFilter: function(filter) {
        var c = document.getElementById('canvas');
        var ctx = c.getContext('2d');
        var imageData = ctx.getImageData(0, 0, c.width, c.height);
        var d = imageData.data;
        var lastImageData = new Array(d.length);
        this.lastImageDataStack.push(lastImageData);
        for(var i=0;i<d.length; i++) {
            lastImageData[i] = d[i];
        }
        filter.call(this, imageData);
        ctx.putImageData(imageData, 0, 0);
        $("#btnUndo").removeAttr("disabled");
    },
    undo: function() {
        if(this.lastImageDataStack.length > 0) {
            var c = document.getElementById('canvas');
            var ctx = c.getContext('2d');
            var imageData = ctx.getImageData(0, 0, c.width, c.height);
            var d = imageData.data;
            var lastImageData = this.lastImageDataStack.pop();
            for(var i=0;i<d.length; i++) {
                d[i] = lastImageData[i];
            }
            ctx.putImageData(imageData, 0, 0);
            if(this.lastImageDataStack.length == 0) {
                $("#btnUndo").attr("disabled", "disabled");
            }
        }
    }
};
$(document).ready(function() {
    $("#myimg").load(function() {
        var ctx = document.getElementById('canvas').getContext('2d');
        var myimg = document.getElementById('myimg');
        ctx.drawImage(myimg, 0, 0, this.width, this.height);
    }).each(function() {
        if(this.complete) $(this).load();
    });
});
$(function() {
    $("#btnGrayscale").click(function( event ) {
        Filters.applyFilter(function(imageData) {
            var d = imageData.data;
            for (var i=0; i<d.length; i+=4) {
                var r = d[i];
                var g = d[i+1];
                var b = d[i+2];
                var v = 0.2126*r + 0.7152*g + 0.0722*b;
                d[i] = d[i+1] = d[i+2] = v
            }
        });
    });
    $("#btnThreshold").click(function( event ) {
        Filters.applyFilter(function(imageData) {
            var d = imageData.data;
            for (var i=0; i<d.length; i+=4) {
                var r = d[i];
                var g = d[i+1];
                var b = d[i+2];
                var v = (0.2126*r + 0.7152*g + 0.0722*b >= 126) ? 255 : 0;
                d[i] = d[i+1] = d[i+2] = v
            }
        });
    });
    $("#btnBrighten").click(function( event ) {
        Filters.applyFilter(function(imageData) {
            var adjustment = 10;
            var d = imageData.data;
            for (var i=0; i<d.length; i+=4) {
                d[i] += adjustment;
                d[i+1] += adjustment;
                d[i+2] += adjustment;
              }
        });
    });
    $("#btnUndo").click(function(event) {
        Filters.undo();
    });
});