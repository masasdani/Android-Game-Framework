package com.blungstudio.game.framework.gl;

import com.blungstudio.game.framework.math.Rectangle;
import com.blungstudio.game.framework.math.Vector2;

public class Sprite extends TextureRegion{

	SpriteBatcher batcher;
	int x = 0;
	int y = 0;
	float angle = 0;
	float width = texture.width;
	float height = texture.height;
	public final Vector2 position;
    public final Rectangle bounds;
    
	public Sprite(SpriteBatcher batcher, Texture texture, float x, float y, float width, float height) {
		super(texture, x, y, width, height);
		this.batcher = batcher;
		this.width = width;
		this.height = height;
		this.position = new Vector2(width/2, y+height/2);
		this.bounds = new Rectangle(0, 0, width, height);
	}
	
	public Sprite scale(float width, float height) {
		this.width = width;
		this.height = height;
		this.bounds.width = width;
		this.bounds.height = height;
		return this;
	}
	
	public Sprite scaleOriginWidth(float width) {
		this.height = width/this.width*this.height;
		this.width = width;
		this.bounds.width = this.width;
		this.bounds.height = this.height;
		return this;
	}
	
	public Sprite scaleOriginHeight(float height) {
		this.width = height/this.height*this.width;
		this.height = height;
		this.bounds.width = this.width;
		this.bounds.height = this.height;
		return this;
	}
	
	public Sprite translate(int x , int y) {
		this.x = x;
		this.y = y;
		this.position.set(x+width/2, y+height/2);
		this.bounds.lowerLeft.set(x, y-35);
		return this;
	}
	
	public Sprite rotate(float angle) {
		this.angle = angle;
		return this;
	}
	
	public void draw() {
		batcher.beginBatch(texture);
		batcher.drawSprite(x+(width/2), y+(height/2), width, height, angle, this);
		batcher.endBatch();
	}
	
	public void drawCentered(){
		batcher.beginBatch(texture);
		batcher.drawSprite(x, y, width, height, angle, this);
		batcher.endBatch();
	}

	public void draw(float x, float y, float width, float height) {
		batcher.beginBatch(texture);
		batcher.drawSprite(x+(width/2), y, width, height, this);
		batcher.endBatch();
	}
	
	public void draw(float x, float y, float width, float height, float angle) {
		batcher.beginBatch(texture);
		batcher.drawSprite(x+(width/2), y+(height/2), width, height, angle, this);
		batcher.endBatch();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public float getAngle() {
		return angle;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
	
	public boolean overlap(Vector2 touch){
		return touch.x > x && touch.x < (x + width) && touch.y > y && touch.y < (y + height);
	}
	
	
	
}
