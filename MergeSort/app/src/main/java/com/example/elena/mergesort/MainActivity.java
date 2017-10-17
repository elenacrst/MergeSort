package com.example.elena.mergesort;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<String> mSortedValues, mInputValues;
    private static SortedListAdapter mAdapter;
    private static EditText mInputEditText;
    private static TextView mAddedValues;
    private static final String BUNDLE_INPUT_VALUES = "input-values";
    private static final String BUNDLE_SORTED_VALUES= "sorted-values";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupRecyclerView();
        mInputEditText = (EditText) findViewById(R.id.edit_name);
        mAddedValues = (TextView) findViewById(R.id.text_added_values);
        mInputValues = new ArrayList<>();
        mSortedValues = new ArrayList<>();

        if (savedInstanceState!=null){
            if(savedInstanceState.containsKey(BUNDLE_INPUT_VALUES)){
                mInputValues = savedInstanceState.getStringArrayList(BUNDLE_INPUT_VALUES);
            }
            if (savedInstanceState.containsKey(BUNDLE_SORTED_VALUES)){
                mSortedValues = savedInstanceState.getStringArrayList(BUNDLE_SORTED_VALUES);
            }
            setTexts();
        }
    }

    private void setTexts(){
        mAdapter.setData(mSortedValues);
        if (mInputValues.size() != 0) mAddedValues.setText(mInputValues.get(0)+"");
        for (int i=1; i<mInputValues.size(); i++){
            mAddedValues.append(", "+mInputValues.get(i));
        }
    }

    public void setupRecyclerView(){
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_sorted);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new SortedListAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    public void reset(View view) {
        resetData();
    }

    public static void resetData(){
        mInputEditText.clearComposingText();
        mAdapter.setData(null);
        mInputValues.clear();
        mSortedValues.clear();
        mAddedValues.setText("");
    }

    public void mergeSort(List<String> toSort)
    {
        if(toSort.size() > 1)
        {
            List<String> temp = toSort.subList(0, toSort.size()/2);

            ArrayList<String> left = new ArrayList<>(0);
            for(String e : temp) left.add(e);

            temp = toSort.subList(toSort.size()/2, toSort.size());

            ArrayList<String> right = new ArrayList<>(0);
            for(String e : temp) right.add(e);

            if(right.size() != 1) mergeSort(right);
            if(left.size() != 1) mergeSort(left);

            toSort.clear();
            toSort.addAll(mergeSortedLists(left, right));
        }
    }


    public List<String> mergeSortedLists(List<String> leftList, List<String> rightList)
    {
        ArrayList<String> list = new ArrayList<>();

        while(!leftList.isEmpty() && !rightList.isEmpty())
        {
            if((leftList.get(0)).compareTo(rightList.get(0)) <= 0)
                list.add(leftList.remove(0));

            else
                list.add(rightList.remove(0));
        }

        while(!leftList.isEmpty())
            list.add(leftList.remove(0));

        while(!rightList.isEmpty())
            list.add(rightList.remove(0));

        return list;
    }

    public void sortValues(View view) {
        mSortedValues.clear();
        for (int i=0; i<mInputValues.size(); i++){
            mSortedValues.add(mInputValues.get(i));
        }
        if (mSortedValues.size()>0){
            mergeSort(mSortedValues);
        }

        mAdapter.setData(mSortedValues);

    }

    public void addValueToUnsortedList(View view) {

        if (mInputEditText.getText() != null && mInputEditText.getText().toString().length()>0){
            mInputValues.add(mInputEditText.getText().toString());
            if (mInputValues.size() == 1){
                mAddedValues.setText(mInputEditText.getText().toString());
            }else{
                mAddedValues.append(", "+mInputEditText.getText().toString());
            }
            mInputEditText.setText("");
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(BUNDLE_INPUT_VALUES,(ArrayList<String>)mInputValues);
        outState.putStringArrayList(BUNDLE_SORTED_VALUES,(ArrayList<String>)mSortedValues);
    }


}
