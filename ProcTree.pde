// initial values for branch width and length
float firstWidth = 18;
float firstLength = 175;

// upper and lower bounds for the random percentage by which branches will be shrunk each iteration
float minScale = .5;
float maxScale = .8;

// lower threshold for branch width and length. recursion will stop when either is reached
float minWidth = 1;
float minLength = 1;

// custom color for branch color
color trunk = color(92,64,51);

// shape object to be loaded with graphic for leaf
PShape leaf;

void setup() {
  // setup the window (size and color)
  fullScreen();
  stroke(trunk);

  // set up the leaf shape
  leaf = loadShape("leaf.svg");

  // make it so lower left is 0,0 now.
  translate(0,height);
  // make it so coords go up for y
  scale(1,-1);
}

void draw() {
}

void mousePressed() {
  // move to mouse position
  translate(mouseX,mouseY);
  // rotate so that measurements are more intuitive
  rotate(radians(180));
  tree(firstWidth * random(minScale,maxScale),firstLength * random(minScale,maxScale));
}

void tree(float w, float l) {
  // if this line is too thin or too short, draw "leaf" and stop
  if (w < minWidth || l < minLength) {
    pushMatrix();
    shape(leaf,0,0,constrain(w*15,3,10),constrain(w*15,3,10));
    popMatrix();
    return;
  }
  // draw current "branch"
  strokeWeight(w);
  line(0,0,0,l);
  
  // go to end of this line
  translate(0,l); 
 
  // come up with 3 random angles for branches
  // first angle -90 to 0
  float angle1 = random(-50, -33);
  // second angle from first angle to first angle plus 90
  float angle2 = random(-33, 33);  
  // third angle from angle2 to angle2 + 90
  float angle3 = random(33, 50);
  // make sure angle3 doesnt go past 75
  angle3 = constrain(angle3,angle2,75);
  
  //make random shrink factors for length and width for first branch
  float shrinkWidth = random(minScale,maxScale);
  float shrinkLength = random(minScale,maxScale);
  
  // push a new matrix for each branch and recurively draw the branches
  pushMatrix();
  rotate(radians(angle1));
  tree(w * shrinkWidth, l * shrinkLength);
  popMatrix();
  
  
  // new shrink factors
  shrinkWidth = random(minScale,maxScale);
  shrinkLength = random(minScale,maxScale);
  pushMatrix();
  rotate(radians(angle2));
  tree(w * shrinkWidth, l * shrinkLength);
  popMatrix();
  
  
  // new shrink factors
  shrinkWidth = random(minScale,maxScale);
  shrinkLength = random(minScale,maxScale);
  pushMatrix();
  rotate(radians(angle3));
  tree(w * shrinkWidth, l * shrinkLength);
  popMatrix();
}