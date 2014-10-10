package org.m3studio.sketchdefense;

import org.m3studio.gameengine.core.Vector;
import org.m3studio.gameengine.core.VisibleGameObject;
import org.m3studio.gameengine.utils.LagrangeInterpolator;
import org.m3studio.gameengine.utils.PositionAnimation;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;

public class FifteensObject extends VisibleGameObject {
	private FifteensScene scene;
	private FifteensPoint fifteensPosition;
	private int value;
	private int size;
	
	private Typeface typeface;
	private Bitmap bitmap;

	public FifteensObject(int value, FifteensPoint pos, int size, float z, Typeface typeface, FifteensScene scene) {
		super(new Vector(pos.j * size, pos.i * size), z);
		
		this.value = value;
		this.fifteensPosition = pos;
		this.size = size;
		
		this.typeface = typeface;
		this.scene = scene;
		
		createBitmap();
	}

	private void createBitmap() {
		this.bitmap = Bitmap.createBitmap(size, size, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		
		Paint rectPaint = new Paint();
		rectPaint.setColor(Color.RED);
		rectPaint.setStrokeWidth(size / 10.0f);
		rectPaint.setStyle(Style.STROKE);
		
		Paint textPaint = new Paint();
		textPaint.setColor(Color.BLACK);
		textPaint.setTypeface(typeface);
		textPaint.setTextSize(size / 2.0f);
		
		canvas.drawColor(Color.GRAY);
		canvas.drawRect(0, 0, size, size, rectPaint);
		
		if (value != 16)
			canvas.drawText(String.valueOf(value), size / 4.0f, 3.0f * size / 4.0f, textPaint);
		
	}
	
	@Override
	public Bitmap getBitmap() {
		return bitmap;
	}

	@Override
	public Rect getBoundingRect() {
		Vector position = getPosition();
		float scale = getScale();
		
		Rect r = new Rect(0, 0, size, size);
		
		r.left *= scale;
		r.top *= scale;
		r.right *= scale;
		r.bottom *= scale;
		
		r.left += position.x;
		r.top += position.y;
		r.right += position.x;
		r.bottom += position.y;
		
		return r;
	}

	@Override
	public boolean isPointInside(Vector position) {
		return true;
	}
	
	public void moveTo(FifteensPoint location) {
		PositionAnimation posAnim = new PositionAnimation(new Vector(fifteensPosition.j * size, fifteensPosition.i * size), new Vector(location.j * size, location.i * size), 0.5f, this, LagrangeInterpolator.class, false);
		
		getEngine().addAnimation(posAnim);
		fifteensPosition = location;
	}
	
	//Events
	@Override
	protected void onTouch() {
		Log.d("Touched!", String.valueOf(fifteensPosition.i) + " " + String.valueOf(fifteensPosition.j));
		
		FifteensPoint movement = scene.getDummyPointLocation();
		
		if (fifteensPosition.isNeighbor(movement)) {
			//Swap the objects
			scene.swap(fifteensPosition, movement);
			
			FifteensObject dummyObject = scene.getDummyObject();
			
			dummyObject.moveTo(fifteensPosition);
			this.moveTo(movement);
		}
	}

}