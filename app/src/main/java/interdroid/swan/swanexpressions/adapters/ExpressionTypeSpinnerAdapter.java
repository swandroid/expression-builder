package interdroid.swan.swanexpressions.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.enums.ExpressionType;

public class ExpressionTypeSpinnerAdapter extends ArrayAdapter<ExpressionType> {

	Context context;
	ExpressionType[] mExpressionTypes;

	public ExpressionTypeSpinnerAdapter(Context context, int textViewResourceId,
										ExpressionType[] expressionTypes) {
		super(context, textViewResourceId, expressionTypes);
		this.context = context;
		this.mExpressionTypes = expressionTypes;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_expression_type_spinner, null);
		}
		TextView type = (TextView) convertView.findViewById(R.id.item_expression_type_name);
		type.setText(type.getContext().getString(mExpressionTypes[position].getNameId()));

		TextView summary = (TextView) convertView.findViewById(R.id.item_expression_type_summary);
		summary.setText(summary.getContext().getString(mExpressionTypes[position].getSummaryId()));

		return convertView;
	}
}