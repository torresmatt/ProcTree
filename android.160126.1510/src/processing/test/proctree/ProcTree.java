package processing.test.proctree;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ProcTree extends PApplet {

float firstWidth = 8;
float firstLength = 75;

float minScale = .5f;
float maxScale = .8f;

float minWidth = 1;
float minLength = 1;

int trunk = color(92,64,51);

PShape leaf;

public void setup() {
  // setup the window (size and color)
  
  stroke(trunk);

  // set up the leaf shape
  leaf = loadShape("leaf.svg");

  // make it so lower left is 0,0 now.
  translate(0,height); 
  scale(1,-1);
}

public void draw() {
}

public void mouseClicked() {
  // move to mouse position
  translate(mouseX,mouseY);
  // just for fun, seed the random generator to where the
  randomSeed((long) random(mouseX, mouseY));
  // rotate so that measurements are more intuitive
  rotate(radians(180));
  tree(firstWidth,firstLength);
}

public void tree(float w, float l) {
  // if this line is too thin or too short, draw "leaf" and stop
  if (w < minWidth || l < minLength) {
    pushMatrix();
    shape(leaf,0,0,7,7);
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
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "ProcTree" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
