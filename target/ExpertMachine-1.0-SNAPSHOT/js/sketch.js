var nodes = [];

var selectNodes = []

var totalLevel = 0;

var canvasX;
var canvasY;

var circleR = 80;

class Node {
    constructor(id, activity, knowledge, level, next) {
        this.id = id;
        this.activity = activity;
        this.knowledge = knowledge;
        this.level = level;
        this.next = next;

        this.x = 0;
        this.y = 0;
    }

}

function preload() {
    let url = '/ExpertMachine/getGraph';
    loadJSON(url, callback = function (data) {
        for (let i = 0; i < data.length; i++) {
            let node = new Node(data[i].id, data[i].activity, data[i].knowledge, data[i].level, data[i].next);
            nodes.push(node);
            if (node.level > totalLevel)
                totalLevel = node.level;
        }
    });
}

function setup() {
    // noLoop();
    let cnv = createCanvas(1200, 900);
    // cnv.position(0, 0, 'relative');
    var x = (windowWidth - width) / 2;
    var y = (windowHeight - height) / 2;
    canvasX = x;
    canvasY = y;
    cnv.position(x, y);

    // cnv.parent("sketch-holder");
    cnv.mousePressed(getNodeWhenPressed);

    button = createButton('Start');
    button.size(80, 30);
    button.position(19, 19);
    button.parent('submit-button');
    button.mousePressed(showNodes);
}

function showNodes() {
    let url = '/ExpertMachine/setInputServlet';
    let msg = []

    for (let j = 0; j < selectNodes.length; j++)
        msg.push(selectNodes[j].knowledge);

    selectNodes = [];

    // $('input[type="checkbox"]:checked').each(function () {//遍历每一个名字为interest的复选框，其中选中的执行函数
    //     msg.push($(this).val());//将选中的值添加到数组chk_value中
    //     console.log($(this).val());
    // });

    let sjson = JSON.stringify(msg);
    // let json = ["a3", "a5", "a4"];
    //
    // var sjson = JSON.stringify(json);

    $.ajax({
        type: "POST",
        url: url,
        data: sjson,
        dataType: "json",
        cache: false,
        success: function (data) {
            for (let i = 0; i < data.length; i++) {
                nodes[i] = new Node(data[i].id, data[i].activity, data[i].knowledge, data[i].level, data[i].next);
            }
        }
    });
}

function getSameLevelNodes(level) {
    let temp = [];
    for (let i = 0; i < nodes.length; i++) {
        if (nodes[i].level === level)
            temp.push(nodes[i]);
    }
    return temp;
}

function drawNode(node, x, y) {
    if (node.activity === true)
        fill(135, 206, 250);
    else
        fill(255);
    ellipse(x, y, circleR, circleR);
    let trueNode = nodes[getNodeIndex(node.id)]
    trueNode.x = x;
    trueNode.y = y;

    textSize(32);
    fill(128, 128, 128);
    text(node.knowledge, x - 10, y + 10);

}

function getNodeIndex(id) {
    for (let i = 0; i < nodes.length; i++) {
        if (nodes[i].id === id)
            return i;
    }
    return -1;
}

function drawEdges() {
    for (let i = 0; i < nodes.length; i++) {
        if (nodes[i].next !== -1) {
            if (nodes[i].activity === true) {
                let index = getNodeIndex(nodes[i].next);
                stroke(0);
                strokeWeight(5);
                if (nodes[index].activity === true)
                    line(nodes[i].x, nodes[i].y, nodes[index].x, nodes[index].y);
            } else {
                let index = getNodeIndex(nodes[i].next);
                stroke(0);
                strokeWeight(1);
                line(nodes[i].x, nodes[i].y, nodes[index].x, nodes[index].y);
            }
        }
    }
    strokeWeight(1);
    stroke(0);
}

function draw() {
    background(255);

    let sx = 80;
    let sy = 80;
    let tempy = sy;
    for (let curLevel = 0; curLevel <= totalLevel; curLevel++) {
        let tempNodes = getSameLevelNodes(curLevel);

        tempy = sy;

        for (let j = 0; j < tempNodes.length; j++) {
            drawNode(tempNodes[j], sx, sy);
            sy += 120;
        }

        sy = tempy + 80;
        sx += 250;
    }

    drawEdges();

}

function disNode(node1, node2) {
    return (node1.x - node2.x) * (node1.x - node2.x) + (node1.y - node2.y) * (node1.y - node2.y);
}

function getNodeWhenPressed() {
    let x = mouseX;
    let y = mouseY;
    let tempNode = new Node();
    tempNode.x = x;
    tempNode.y = y;

    for (let i = 0; i < nodes.length; i++) {
        if (disNode(tempNode, nodes[i]) <= circleR * circleR) {
            let p = false;
            for (let j = 0; j < selectNodes.length; j++) {
                if (selectNodes[j] === nodes[i])
                    p = true;
            }
            if (!p) {
                selectNodes.push(nodes[i]);
                break;
            }
        }
    }

}