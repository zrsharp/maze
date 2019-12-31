// JavaScript Document

var array;
var path;
var playerSize = 15;
var blockSize = 15;
var handle = null;
var rowToSave;
var colToSave;
var background = [ "../image/back1.gif", "../image/back2.gif",
		"../image/back3.gif", "../image/back4.gif", "../image/back5.gif",
		"../image/back6.gif", "../image/back7.gif", "../image/back8.gif",
		"../image/back9.gif" ];
var imgCount = 0;
$(document).ready(function() {

	$("#getMaze").click(function() {
		$("#main_area").empty();
		if (handle != null) {
			clearInterval(handle);
			handle = null;
		}
		getMaze();
		GenerateMaze(array);
	});

	$("#start").click(function() {
		if (!document.getElementById("player")) {
			PlayerMove(path);
		}
	});

	$("#createPlayer").click(function() {
		if (!document.getElementById("player")) {
			createPlayer();
			playerMoveByKeyboard();
		}
	});

	Save();
	changeBackground();

});

function GenerateMaze(array) {

	$("#main_area").css("height", ($("#row").val()) * blockSize + "px");
	$("#main_area").css("width", ($("#col").val()) * blockSize + "px");
	for (var i = 0; i < array.length; i++) {
		for (var j = 0; j < array[i].length; j++) {
			// 生成墙
			if (array[i][j] == 0) {
				$("#main_area").append(
						'<div style="background-color:black; left:' + j
								* blockSize + 'px; top:' + i * blockSize
								+ 'px; height:' + blockSize + 'px; width:'
								+ blockSize + 'px; position:absolute;"></div>');
			}

			// 生成起点
			if (array[i][j] == 2) {
				$("#main_area").append(
						'<div style="background-color:green; left:' + j
								* blockSize + 'px; top:' + i * blockSize
								+ 'px; height:' + blockSize + 'px; width:'
								+ blockSize + 'px; position:absolute;"></div>');
			}
			// 生成终点
			if (array[i][j] == 3) {
				$("#main_area").append(
						'<div style="background-color:yellow; left:' + j
								* blockSize + 'px; top:' + i * blockSize
								+ 'px; height:' + blockSize + 'px; width:'
								+ blockSize + 'px; position:absolute;"></div>');
			}

		}
	}
}

function PlayerMove(path) {

	var rownum = path[0].y;
	var colnum = path[0].x;

	$("#main_area").append(
			'<div id="player" style="background-color:blue; left:' + colnum
					* playerSize + 'px; top:' + rownum * playerSize
					+ 'px; height:' + playerSize + 'px; width:' + playerSize
					+ 'px; position:absolute;"></div>');

	var count = 1;
	handle = setInterval(function() {
		if (count >= path.length) {
			clearInterval(handle);
			handle = null;
			$("#msg").text("迷宫结束");
		}
		rownum = path[count].y;
		colnum = path[count].x;
		$("#player").css("left", colnum * playerSize + 'px');
		$("#player").css("top", rownum * playerSize + 'px');
		count++;
	}, 300);
}

function playerMoveByKeyboard() {
	var rownum;
	var colnum;
	$(document).unbind("keypress");
	$(document).keypress(
			function(e) {
				// 上119 下115 左97 右100
				rownum = parseInt($("#player").css("top")) / playerSize;
				colnum = parseInt($("#player").css("left")) / playerSize;
				console.log("rownum = " + rownum + "  colnum = " + colnum);
				switch (e.which) {
				case 119:
					console.log("e.which = " + e.which + " array["
							+ (rownum - 1) + "][" + colnum + "] == "
							+ array[rownum - 1][colnum])

					if (array[rownum - 1][colnum] == 1) {
						$("#player").css("left", colnum * playerSize + 'px');
						$("#player").css("top",
								(rownum - 1) * playerSize + 'px');
					}
					break;

				case 115:
					console.log("e.which = " + e.which + " array["
							+ (rownum + 1) + "][" + colnum + "] == "
							+ array[rownum + 1][colnum])
					if (array[rownum + 1][colnum] == 1) {
						$("#player").css("left", colnum * playerSize + 'px');
						$("#player").css("top",
								(rownum + 1) * playerSize + 'px');
					}
					break;

				case 97:
					console.log("e.which = " + e.which + " array[" + rownum
							+ "][" + (colnum - 1) + "] == "
							+ array[rownum][colnum - 1])
					if (array[rownum][colnum - 1] == 1) {
						$("#player").css("left",
								(colnum - 1) * playerSize + 'px');
						$("#player").css("top", rownum * playerSize + 'px');
					}
					break;

				case 100:
					console.log("e.which = " + e.which + " array[" + rownum
							+ "][" + (colnum + 1) + "] == "
							+ array[rownum][colnum + 1])
					if (array[rownum][colnum + 1] == 1) {
						$("#player").css("left",
								(colnum + 1) * playerSize + 'px');
						$("#player").css("top", rownum * playerSize + 'px');
					}
					break;

				default:
					break;
				}
			});
}

function createPlayer() {
	var rownum = path[0].y;
	var colnum = path[0].x;
	$("#main_area").append(
			'<div id="player" style="background-color:blue; left:' + colnum
					* playerSize + 'px; top:' + rownum * playerSize
					+ 'px; height:' + playerSize + 'px; width:' + playerSize
					+ 'px; position:absolute;"></div>');
}

function Save() {
	$("#mazeSub").click(function() {
		$.ajax({
			url : "/player/savePlayerInfo",
			type : "POST",
			data : {
				playerName : $("#name").val(),
				mazeMap : JSON.stringify(array),
				row : rowToSave,
				col : colToSave,
				playerX : parseInt($("#player").css("left")) / playerSize,
				playerY : parseInt($("#player").css("top")) / playerSize,
				hasGoneThrough : true
			},
			async : false
		});
	});
}

// 获取迷宫
function getMaze() {
	$.ajax({
		url : "/maze/getMaze",
		type : "GET",
		data : {
			row : $("#row").val(),
			col : $("#col").val(),
			pattern : 1
		},
		async : false,
		dataType : "JSON",
		success : function(contents) {

			if (contents.success) {
				array = JSON.parse(JSON.stringify(contents.data.mazeMap));
				path = JSON.parse(JSON.stringify(contents.data.path));
				rowToSave = contents.data.row;
				colToSave = contents.data.col;
			}
		}
	});

}

function changeBackground() {
	$("#changeBackground").click(function() {
		imgCount++;
		if (imgCount >= background.length) {
			imgCount = 0;
		}
		console.log(imgCount);
		$("#backImg").attr("src", background[imgCount]);
	});
}