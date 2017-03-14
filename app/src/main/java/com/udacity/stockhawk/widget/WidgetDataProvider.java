package com.udacity.stockhawk.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by yehia on 10/03/17.
 */

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {


    private final DecimalFormat dollarFormatWithPlus;
    private final DecimalFormat dollarFormat;
    private final DecimalFormat percentageFormat;


    Context mContext = null;
    Cursor data = null;
    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
        dollarFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormatWithPlus = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormatWithPlus.setPositivePrefix("+$");
        percentageFormat = (DecimalFormat) NumberFormat.getPercentInstance(Locale.getDefault());
        percentageFormat.setMaximumFractionDigits(2);
        percentageFormat.setMinimumFractionDigits(2);
        percentageFormat.setPositivePrefix("+");
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return data.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        data.moveToPosition(position);

        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                R.layout.list_item_quote);
        String price=String.valueOf(data.getFloat(Contract.Quote.POSITION_PRICE));
       String symbol= data.getString(Contract.Quote.POSITION_SYMBOL);
        float rawAbsoluteChange =  data.getFloat(Contract.Quote.POSITION_ABSOLUTE_CHANGE);
        String change = dollarFormatWithPlus.format(rawAbsoluteChange);


        view.setTextViewText(R.id.symbol, symbol);
        view.setTextViewText(R.id.price,price);
        view.setTextViewText(R.id.change,change+"");
        if (rawAbsoluteChange > 0) {
            view.setInt(R.id.change, "setBackgroundResource",  R.drawable.percent_change_pill_green);

        } else {

            view.setInt(R.id.change, "setBackgroundResource", R.drawable.percent_change_pill_red);
        }


        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void initData() {
        data = mContext.getContentResolver().query(
                Contract.Quote.URI,
                Contract.Quote.QUOTE_COLUMNS,
                null, null, Contract.Quote.COLUMN_SYMBOL);


    }

}