package org.m3studio.gameengine.core;

import android.graphics.Matrix;

public class GameObject implements Comparable<GameObject> {
	private Engine engine;
	
	private Vector position;
	private float scale;
	private float angle;
	private float z;
	
	protected Matrix transformationMatrix;
	
	protected EventDispatcher dispatcher;
	
	public GameObject(Vector position, float z) {
		this.position = position;
		this.scale = 1.0f;
		this.angle = 0.0f;
		this.z = z;
		
		this.transformationMatrix = new Matrix();
		
		this.dispatcher = new EventDispatcher(this);
	}
	
	public GameObject(Vector position) {
		this(position, 0.0f);
	}
	
	public GameObject() {
		this(new Vector(0, 0));
	}
	
	public final void setEngine(Engine engine) {
		this.engine = engine;
	}
	
	protected final Engine getEngine() {
		return engine;
	}
	
	public final Vector getPosition() {
		Vector result = (Vector) ResourceFactory.getInstance().obtainObject(Vector.class);
		
		synchronized (position) {
			result.set(position);
		}
		
		return result;
	}
	
	public final float getScale() {
		return scale;
	}
	
	public final float getAngle() {
		return angle;
	}
	
	public final float getZ() {
		return z;
	}
	
	public Matrix getMatrix() {
		transformationMatrix.reset();
		
		Vector positionVal = getPosition();
		
		
		transformationMatrix.postScale(scale, scale);
		transformationMatrix.postRotate((float) Math.toDegrees(angle));
		
		transformationMatrix.postTranslate(positionVal.x, positionVal.y);
		
		ResourceFactory.getInstance().releaseObject(positionVal);
		
		return transformationMatrix;
	}
	
	public final void setPosition(Vector position) {
		this.position.set(position);
		
		dispatcher.setChanged("Position");
	}
	
	public final void setScale(float scale) {
		this.scale = scale;
		
		dispatcher.setChanged("Scale");
	}
	
	public final void setAngle(float angle) {
		this.angle = angle;
		
		dispatcher.setChanged("Angle");
	}
	
	public final void setZ(float z) {
		this.z = z;
		
		dispatcher.setChanged("Z");
	}
	
	public final void dispatchEvents() {
		dispatcher.invokeEvent();
	}
	
	public void update(long step) {

	}

	@Override
	public final int compareTo(GameObject another) {
		if (z > another.z)
			return 1;
		else
			return -1;
	}
}