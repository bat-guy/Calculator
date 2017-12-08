package com.example.dheeraj.calculator;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.dheeraj.calculator.databinding.ActivityMainBinding;

/**
 * Created by Dheeraj on 05-12-2017.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener, com.example.dheeraj.calculator.View {

    private ActivityMainBinding mBinding;
    private Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        presenter = new PresenterImpl(this);

        mBinding.tvAdd.setOnClickListener(this);
        mBinding.tvC.setOnClickListener(this);
        mBinding.tvCE.setOnClickListener(this);
        mBinding.tvDecimal.setOnClickListener(this);
        mBinding.tvDisplay.setOnClickListener(this);
        mBinding.tvDivide.setOnClickListener(this);
        mBinding.tvEight.setOnClickListener(this);
        mBinding.tvExponent.setOnClickListener(this);
        mBinding.tvFive.setOnClickListener(this);
        mBinding.tvFour.setOnClickListener(this);
        mBinding.tvMinus.setOnClickListener(this);
        mBinding.tvModulus.setOnClickListener(this);
        mBinding.tvMultiply.setOnClickListener(this);
        mBinding.tvNine.setOnClickListener(this);
        mBinding.tvOne.setOnClickListener(this);
        mBinding.tvSeven.setOnClickListener(this);
        mBinding.tvSix.setOnClickListener(this);
        mBinding.tvThree.setOnClickListener(this);
        mBinding.tvTotal.setOnClickListener(this);
        mBinding.tvTwo.setOnClickListener(this);
        mBinding.tvZero.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvAdd:
                presenter.addClicked();
                break;
            case R.id.tvC:
                presenter.cancelClicked();
                break;
            case R.id.tvCE:
                presenter.ceClicked();
                break;
            case R.id.tvDecimal:
                presenter.decimalClicked();
                break;
            case R.id.tvDivide:
                presenter.divideClicked();
                break;
            case R.id.tvEight:
                presenter.digitClicked(8);
                break;
            case R.id.tvExponent:
                presenter.powerClicked();
                break;
            case R.id.tvFive:
                presenter.digitClicked(5);
                break;
            case R.id.tvFour:
                presenter.digitClicked(4);
                break;
            case R.id.tvMinus:
                presenter.minusClicked();
                break;
            case R.id.tvModulus:
                presenter.modulusClicked();
                break;
            case R.id.tvMultiply:
                presenter.multiplyClicked();
                break;
            case R.id.tvNine:
                presenter.digitClicked(9);
                break;
            case R.id.tvOne:
                presenter.digitClicked(1);
                break;
            case R.id.tvSeven:
                presenter.digitClicked(7);
                break;
            case R.id.tvSix:
                presenter.digitClicked(6);
                break;
            case R.id.tvThree:
                presenter.digitClicked(3);
                break;
            case R.id.tvTotal:
                presenter.totalClicked();
                break;
            case R.id.tvTwo:
                presenter.digitClicked(2);
                break;
            case R.id.tvZero:
                presenter.digitClicked(0);
                break;
        }
    }

    /**
     * Method called to set the text on the displayTextView
     * @param value the text value
     */
    @Override
    public void setText(String value) {
        mBinding.tvDisplay.setText(value);
    }

    /**
     * Method called to get the value of displayTextView
     * @return the value of display textView
     */
    @Override
    public String getTextValue() {
        return mBinding.tvDisplay.getText().toString();
    }

    /**
     * Method to show the error toast
     */
    @Override
    public void showToast() {
        Toast.makeText(this, getResources().getString(R.string.bad_expression), Toast.LENGTH_SHORT).show();
    }
}