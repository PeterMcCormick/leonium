package main.utils.browserutils;

@SuppressWarnings("rawtypes")
public class BrowserHandlerOptions {
	public final BrowserOption screenshotOnClick = new BrowserOption();
	public final BrowserOption screenshotOnSelect = new BrowserOption();
	public final BrowserOption screenshotOnSendKeys = new BrowserOption();
	public final BrowserOption continueOnException = new BrowserOption();
	public final BrowserOption logOnWait = new BrowserOption();
	public final BrowserOption logAutonomously = new BrowserOption();
	public final BrowserOption defaultWait = new BrowserOption();

	public class BrowserOption<T> {
		private boolean isEnabled;
		private T value;

		public BrowserOption() {
		}

		public BrowserOption(T value) {
		}

		public boolean isEnabled() {
			return this.isEnabled;
		}

		public void setEnabled(boolean enable) {
			this.isEnabled = enable;
		}

		public void setValue(T value) {
			this.value = value;
		}

		public T getValue() {
			return this.value;
		}
	}
}