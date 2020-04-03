public class MyFragment extends Fragment implements FragmentUserVisibleController.UserVisibleCallback{
	private FragmentUserVisibleController userVisibleController;

	public MyFragment() {
		userVisibleController = new FragmentUserVisibleController(this, this);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		userVisibleController.activityCreated();
	}

	@Override
	public void onResume() {
		super.onResume();
		userVisibleController.resume();
	}

	@Override
	public void onPause() {
		super.onPause();
		userVisibleController.pause();
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		userVisibleController.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public void setWaitingShowToUser(boolean waitingShowToUser) {
		userVisibleController.setWaitingShowToUser(waitingShowToUser);
	}

	@Override
	public boolean isWaitingShowToUser() {
		return userVisibleController.isWaitingShowToUser();
	}

	@Override
	public boolean isVisibleToUser() {
		return userVisibleController.isVisibleToUser();
	}

	@Override
	public void callSuperSetUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public void onVisibleToUserChanged(boolean isVisibleToUser, boolean invokeInResumeOrPause) {

	}
}