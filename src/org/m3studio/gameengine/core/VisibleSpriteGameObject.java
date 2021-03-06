package org.m3studio.gameengine.core;

import org.m3studio.gameengine.utils.BasicFramesAnimation;
import org.m3studio.gameengine.utils.LagrangeInterpolator;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;

//TODO Implement atomicity for all the methods

public abstract class VisibleSpriteGameObject extends VisibleGameObject {
	private Sprite sprite;
	private BasicFramesAnimation animation;
	private int frameNum;
	
	public VisibleSpriteGameObject(Sprite sprite, Vector position, float z) {
		super(position, z);
		this.sprite = sprite;
		this.frameNum = 0;
		
		animation = new BasicFramesAnimation(0.6f, this, LagrangeInterpolator.class, true);
	}
	
	public VisibleSpriteGameObject(Sprite sprite) {
		this(sprite, new Vector(0, 0), 0.0f);
	}
		
	public final Sprite getSprite() {
		return sprite;
	}
	
	public final int getFrameNum() {
		return frameNum;
	}
	
	@Override
	public Bitmap getBitmap() {
		return sprite.getBitmap(frameNum);
	}
	
	@Override
	public RectF getBoundingRect() {
		RectF r = sprite.getBoundingRect();
		
		Matrix transformationMatrix = getMatrix();
		transformationMatrix.mapRect(r);
		ResourceFactory.getInstance().releaseObject(transformationMatrix);
		
		return r;
	}
	
	public final void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public final void setFrameNum(int frameNum) {
		this.frameNum = frameNum;
	}
	
	public final void addAnimationToAnimationPipeline() {
		this.getEngine().addAnimation(animation);
	}
	
	public final void removeAnimationFromAnimationPipeline() {
		this.getEngine().removeAnimation(animation);
	}
	
	@Override
	public boolean isPointInside(Vector position) {
		//TODO
		return true;
	}
	
	@Override
	public Matrix getMatrix() {
		Vector spriteCoords = sprite.getCoords();
		
		Matrix matrix = super.getMatrix();
		matrix.preTranslate(-spriteCoords.x, -spriteCoords.y);
		
		return matrix;
	}
}
