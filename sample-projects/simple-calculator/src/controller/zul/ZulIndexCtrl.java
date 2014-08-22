package controller.zul;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import cococare.common.CCConfig;
import cococare.common.CCFormat;
import cococare.common.CCMath;
import cococare.common.CCFieldConfig.Type;
import cococare.zk.CCAfterMount;
import cococare.zk.CCZk;

public class ZulIndexCtrl extends Window {
	// view-component
	private Button btn0;
	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;
	private Button btn5;
	private Button btn6;
	private Button btn7;
	private Button btn8;
	private Button btn9;
	private Button btnAddition;
	private Button btnBackspace;
	private Button btnC;
	private Button btnCE;
	private Button btnDivision;
	private Button btnEqual;
	private Button btnMultiplication;
	private Button btnPoint;
	private Button btnPower;
	private Button btnSign;
	private Button btnSquareRoot;
	private Button btnSubtraction;
	private Textbox txtLabelCurr;
	private Textbox txtLabelPrev;
	//
	private boolean operator = true;
	private double lastDigit = 0;
	private String lastOperator = "";

	public void onCreate() {
		// basic config
		CCConfig.FMT_FRACTION_DIGITS_ALWAYS_SHOW = false;
		//
		initComponents();
		_init();
	}

	private void initComponents() {
		CCZk.initComponent(this, this, null);
	}

	private void _init() {
		CCAfterMount.applyFormatStyle(txtLabelPrev, Type.DECIMAL);
		CCAfterMount.applyFormatStyle(txtLabelCurr, Type.DECIMAL);
		EventListener elTypingCurr = new EventListener() {
			public void onEvent(Event event) throws Exception {
				_typingCurr(((Button) event.getTarget()).getLabel());
			}
		};
		CCZk.addEventListenerOnClick(btnPoint, elTypingCurr);
		CCZk.addEventListenerOnClick(btn0, elTypingCurr);
		CCZk.addEventListenerOnClick(btn1, elTypingCurr);
		CCZk.addEventListenerOnClick(btn2, elTypingCurr);
		CCZk.addEventListenerOnClick(btn3, elTypingCurr);
		CCZk.addEventListenerOnClick(btn4, elTypingCurr);
		CCZk.addEventListenerOnClick(btn5, elTypingCurr);
		CCZk.addEventListenerOnClick(btn6, elTypingCurr);
		CCZk.addEventListenerOnClick(btn7, elTypingCurr);
		CCZk.addEventListenerOnClick(btn8, elTypingCurr);
		CCZk.addEventListenerOnClick(btn9, elTypingCurr);
		EventListener elTypingSign = new EventListener() {
			public void onEvent(Event event) throws Exception {
				_typingCurrSign();
			}
		};
		CCZk.addEventListenerOnClick(btnSign, elTypingSign);
		EventListener elTypingBackspace = new EventListener() {
			public void onEvent(Event event) throws Exception {
				_typingCurrBackspace();
			}
		};
		CCZk.addEventListenerOnClick(btnBackspace, elTypingBackspace);
		EventListener elClearCurr = new EventListener() {
			public void onEvent(Event event) throws Exception {
				_clearCurr();
			}
		};
		CCZk.addEventListenerOnClick(btnCE, elClearCurr);
		EventListener elTypingOperator = new EventListener() {
			public void onEvent(Event event) throws Exception {
				_typingOperator(((Button) event.getTarget()).getLabel());
			}
		};
		CCZk.addEventListenerOnClick(btnSquareRoot, elTypingOperator);
		CCZk.addEventListenerOnClick(btnPower, elTypingOperator);
		CCZk.addEventListenerOnClick(btnMultiplication, elTypingOperator);
		CCZk.addEventListenerOnClick(btnDivision, elTypingOperator);
		CCZk.addEventListenerOnClick(btnAddition, elTypingOperator);
		CCZk.addEventListenerOnClick(btnSubtraction, elTypingOperator);
		EventListener elTypingEqual = new EventListener() {
			public void onEvent(Event event) throws Exception {
				_typingEqual();
			}
		};
		CCZk.addEventListenerOnClick(btnEqual, elTypingEqual);
		EventListener elClearAll = new EventListener() {
			public void onEvent(Event event) throws Exception {
				_clearAll();
			}
		};
		CCZk.addEventListenerOnClick(btnC, elClearAll);
	}

	private void _typingCurr(String string) {
		String labelCurr = operator ? "0" : txtLabelCurr.getText();
		if (".".equals(string)) {
			if (!labelCurr.contains(".")) {
				txtLabelCurr.setText(labelCurr + string);
			}
		} else if ("0".equals(labelCurr)) {
			txtLabelCurr.setText(string);
		} else {
			String digit = CCFormat.getDigit(labelCurr);
			if (digit.length() < 16) {
				txtLabelCurr.setText(labelCurr + string);
			}
		}
		operator = false;
		lastDigit = Double.parseDouble(txtLabelCurr.getText());
	}

	private void _typingCurrSign() {
		String labelCurr = txtLabelCurr.getText();
		if (!"0".equals(labelCurr)) {
			if (labelCurr.contains("-")) {
				txtLabelCurr.setText(labelCurr.replaceFirst("-", ""));
			} else {
				txtLabelCurr.setText("-" + labelCurr);
			}
		}
	}

	private void _typingCurrBackspace() {
		String labelCurr = txtLabelCurr.getText();
		if (!"0".equals(labelCurr)) {
			if (labelCurr.length() > 1) {
				txtLabelCurr.setText(labelCurr.substring(0, labelCurr.length() - 1));
			} else {
				txtLabelCurr.setText("0");
			}
		}
	}

	private void _clearCurr() {
		txtLabelCurr.setText("0");
		operator = false;
	}

	private void _typingOperator(String string) {
		String labelCurr = txtLabelCurr.getText();
		String labelPrev = txtLabelPrev.getText();
		if (operator) {
			if (labelPrev.length() > 1) {
				labelPrev = labelPrev.substring(0, labelPrev.length() - 1);
			}
			txtLabelPrev.setText(labelPrev + string);
		} else {
			txtLabelPrev.setText(labelPrev + labelCurr + string);
			operator = true;
		}
		labelPrev = txtLabelPrev.getText();
		if (labelPrev.length() > 1) {
			labelPrev = labelPrev.substring(0, labelPrev.length() - 1);
		}
		txtLabelCurr.setText(CCFormat.formatDecimal(CCMath.calculate(labelPrev)));
		lastOperator = string;
	}

	private void _typingEqual() {
		String labelCurr = txtLabelCurr.getText();
		String labelPrev = txtLabelPrev.getText();
		if (labelPrev.isEmpty()) {
			if (!lastOperator.isEmpty()) {
				txtLabelCurr.setText(CCFormat.formatDecimal(CCMath.calculate(labelCurr + lastOperator + lastDigit)));
			}
		} else {
			txtLabelPrev.setText("");
			txtLabelCurr.setText(CCFormat.formatDecimal(CCMath.calculate(labelPrev + labelCurr)));
			lastDigit = Double.parseDouble(labelCurr);
		}
		operator = true;
	}

	private void _clearAll() {
		txtLabelPrev.setText("");
		txtLabelCurr.setText("0");
		operator = false;
		lastDigit = 0;
		lastOperator = "";
	}
}