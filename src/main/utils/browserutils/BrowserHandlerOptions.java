package main.utils.browserutils;

public class BrowserHandlerOptions {
	public final BrowserOption<Boolean> screenshotOnEvent = screenshotOnEvent();
	public final BrowserOption<Boolean> screenshotOnClick = new BrowserOption<Boolean>();
	public final BrowserOption<Boolean> screenshotOnSelect = new BrowserOption<Boolean>();
	public final BrowserOption<Boolean> screenshotOnSendKeys = new BrowserOption<Boolean>();

	public final BrowserOption<Boolean> continueOnNoSuchElement = new BrowserOption<Boolean>();
	public final BrowserOption<Boolean> continueOnTimeout = new BrowserOption<Boolean>();
	public final BrowserOption<Boolean> reportOnWait = new BrowserOption<Boolean>();
	public final BrowserOption<Boolean> repportAutonomously = new BrowserOption<Boolean>();
	public final BrowserOption<Integer> defaultWait = new BrowserOption<Integer>(15);

	public class BrowserOption<T> {
		protected T value;

		public BrowserOption() {
		}

		public BrowserOption(T value) {
		}

		public void setValue(T value) {
			this.value = value;
		}

		public T getValue() {
			return this.value;
		}
	}

	private BrowserOption<Boolean> screenshotOnEvent() {
		return new BrowserOption<Boolean>() {
			public void setValue(Boolean value) {
				this.value = value;
				screenshotOnClick.setValue(value);
				screenshotOnSendKeys.setValue(value);
				screenshotOnSelect.setValue(value);
			}
		};
	}
}