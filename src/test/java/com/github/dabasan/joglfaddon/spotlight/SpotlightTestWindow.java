package com.github.dabasan.joglfaddon.spotlight;

import static com.github.dabasan.basis.coloru8.ColorU8Functions.*;
import static com.github.dabasan.basis.matrix.MatrixFunctions.*;
import static com.github.dabasan.basis.vector.VectorFunctions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.dabasan.basis.matrix.Matrix;
import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.joglf.gl.front.LightingFront;
import com.github.dabasan.joglf.gl.model.Model3DFunctions;
import com.github.dabasan.joglf.gl.shader.ShaderProgram;
import com.github.dabasan.joglf.gl.window.JOGLFWindow;
import com.github.dabasan.tool.MathFunctions;

class SpotlightTestWindow extends JOGLFWindow {
	private List<Integer> spotlight_handles;
	private int plane_handle;

	@Override
	public void Init() {
		SpotlightMgr.Initialize();
		spotlight_handles = new ArrayList<>();

		Random random = new Random();
		for (int i = 0; i < 1; i++) {
			int spotlight_handle = SpotlightMgr.CreateSpotlight();

			Vector position = VGet(50.0f, 50.0f, 50.0f);
			float deg = random.nextFloat() * 360.0f;
			float rad = MathFunctions.DegToRad(deg);
			Matrix rot_y = MGetRotY(rad);
			position = VTransform(position, rot_y);

			SpotlightMgr.SetPositionAndTarget(spotlight_handle, position,
					VGet(0.0f, 0.0f, 0.0f));

			float r = random.nextFloat();
			float g = random.nextFloat();
			float b = random.nextFloat();
			SpotlightMgr.SetDiffuseColor(spotlight_handle, GetColorU8(r, g, b, 1.0f));

			spotlight_handles.add(spotlight_handle);
		}

		LightingFront.SetAmbientColor(GetColorU8(0.0f, 0.0f, 0.0f, 0.0f));

		plane_handle = Model3DFunctions.LoadModel("./Data/Model/OBJ/Plane/plane.obj");
		Model3DFunctions.RemoveAllPrograms(plane_handle);
		Model3DFunctions.AddProgram(plane_handle, new ShaderProgram("dabasan/spotlight"));
	}

	@Override
	public void Update() {
		SpotlightMgr.Update();
	}

	@Override
	public void Draw() {
		Model3DFunctions.DrawModel(plane_handle);
	}
}