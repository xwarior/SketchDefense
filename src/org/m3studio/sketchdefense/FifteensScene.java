package org.m3studio.sketchdefense;

import java.util.ArrayList;
import java.util.Collections;

import org.m3studio.gameengine.core.Engine;
import org.m3studio.gameengine.core.ResourceFactory;
import org.m3studio.gameengine.core.Scene;

import android.graphics.Typeface;

public class FifteensScene extends Scene {
	private int matrix[][];
	private FifteensObject dummyObject;
	private final int size = 100;

	public FifteensScene(Engine engine) {
		super(engine);
		
		matrix = new int[4][4];
	}
	
	private boolean checkSolution() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (matrix[i][j] != (i * 4 + j))
					return false;
			}
		}
		
		return true;
	}
	
	public void swap(FifteensPoint a, FifteensPoint b) {
		int k = matrix[a.i][a.j];
		matrix[a.i][a.j] = matrix[b.i][b.j];
		matrix[b.i][b.j] = k; 
	}
	
	public FifteensObject getDummyObject() {
		return dummyObject;
	}
	
	public FifteensPoint getDummyPointLocation() {
		FifteensPoint result = new FifteensPoint();
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (matrix[i][j] == 16) {
					result.i = i;
					result.j = j;
					
					return result;
				}
			}
		}
		
		return result;
	}

	@Override
	protected void onSceneLoad() {
		//Get engine
		Engine engine = getEngine();
		
		//Preload font
		Typeface typeface = ResourceFactory.getInstance().makeTypefaceFromAsset("fonts/sketchdefense.otf");
		
		//Init matrix and objects
		ArrayList<Integer> values = new ArrayList<Integer>();
		
		for (int i = 1; i <= 16; i++)
			values.add(i);
		
		Collections.shuffle(values);
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				matrix[i][j] = values.get(4 * i + j);
				FifteensObject obj = new FifteensObject(matrix[i][j], new FifteensPoint(i, j), size, 0.0f, typeface, this);
				
				if (matrix[i][j] == 16)
					dummyObject = obj;
				
				engine.addVisibleGameObject(obj);
			}
		}
		
	}

	@Override
	protected void onSceneRun() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onSceneEnd() {
		// TODO Auto-generated method stub

	}

}