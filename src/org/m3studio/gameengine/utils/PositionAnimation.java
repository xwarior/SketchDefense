package org.m3studio.gameengine.utils;

import org.m3studio.gameengine.core.Animation;
import org.m3studio.gameengine.core.GameObject;
import org.m3studio.gameengine.core.Interpolator;
import org.m3studio.gameengine.core.Vector;

public class PositionAnimation extends Animation {
	private Interpolator xInterpolator;
	private Interpolator yInterpolator;

	public PositionAnimation(Vector start, Vector finish, float animationTime, GameObject target, Class<? extends Interpolator> interpolationBuilder, boolean isLooped) {
		super(target, interpolationBuilder, isLooped);
		
		this.xInterpolator = makeInterpolator();
		this.yInterpolator = makeInterpolator();
		
		this.xInterpolator.addPoint(new Vector(0.0f, start.x));
		this.yInterpolator.addPoint(new Vector(0.0f, start.y));
		
		this.xInterpolator.addPoint(new Vector(animationTime, finish.x));
		this.yInterpolator.addPoint(new Vector(animationTime, finish.y));
		
		this.setEndingTime(animationTime);
	}

	@Override
	public void step() {
		getTarget().setPosition(new Vector(xInterpolator.interpolate(getCurrentTime()), yInterpolator.interpolate(getCurrentTime())));
	}

}
