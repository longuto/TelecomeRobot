package com.yys.telecomrobot.module.openaccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yys.telecomrobot.R;
import com.yys.telecomrobot.app.G;
import com.yys.telecomrobot.base.BaseTtsActivity;
import com.yys.telecomrobot.module.openaccount.packdetail.PackDetailFm;
import com.yys.telecomrobot.module.start.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yys.telecomrobot.app.G.CURR;
import static com.yys.telecomrobot.app.G.NEXT;
import static com.yys.telecomrobot.app.G.PRE;

public class OpenaccountActivity extends BaseTtsActivity {

    @BindView(R.id.iv_one) ImageView one;
    @BindView(R.id.iv_two_p) ImageView twoP;
    @BindView(R.id.iv_two) ImageView two;
    @BindView(R.id.iv_three_p) ImageView threeP;
    @BindView(R.id.iv_three) ImageView three;
    @BindView(R.id.iv_four_p) ImageView fourP;
    @BindView(R.id.iv_four) ImageView four;
    @BindView(R.id.iv_tohome) ImageView mToHomeIv;

    @OnClick(R.id.iv_tohome)
    public void tomain(View view) {
        toMainAct();
    }

    /** 返回首页 */
    private void toMainAct() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        close();
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        PackDetailFm fragment = new PackDetailFm();
        Bundle bundle = new Bundle();
        bundle.putString(G.OPENACCOUNTPACK, getIntent().getStringExtra(G.OPENACCOUNTPACK));
        fragment.setArguments(bundle);
        replaceFragment(R.id.fly_content, fragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_openaccount;
    }

    // 设置指定ImageView的状态, 状态值为PRE CURR NEXT  此方法的值是根据逻辑来的,不是任意填写的
    private void setImageState(int ivId, int state) {
        switch (ivId) {
            case R.id.iv_one:      // 套餐详情
                switch (state) {
                    case CURR:
                        one.setBackgroundResource(R.drawable.mark_one_curr);
                        break;
                    case NEXT:
                        one.setBackgroundResource(R.drawable.mark_one_next);
                        break;
                    default:
                        break;
                }
                break;
            case R.id.iv_two_p:    // 选号码p

                switch (state) {
                    case PRE:
                        twoP.setBackgroundResource(R.drawable.mark_progress_pre);
                        break;
                    case CURR:
                        twoP.setBackgroundResource(R.drawable.mark_progress_curr);
                        break;
                    case NEXT:
                        twoP.setBackgroundResource(R.drawable.mark_progress_next);
                        break;
                    default:
                        break;
                }
                break;
            case R.id.iv_two:      //选号码
                switch (state) {
                    case PRE:
                        two.setBackgroundResource(R.drawable.mark_two_pre);
                        break;
                    case CURR:
                        two.setBackgroundResource(R.drawable.mark_two_curr);
                        break;
                    case NEXT:
                        two.setBackgroundResource(R.drawable.mark_two_next);
                        break;
                    default:
                        break;
                }
                break;
            case R.id.iv_three_p:    // 身份录入p
                switch (state) {
                    case PRE:
                        threeP.setBackgroundResource(R.drawable.mark_progress_pre);
                        break;
                    case CURR:
                        threeP.setBackgroundResource(R.drawable.mark_progress_curr);
                        break;
                    case NEXT:
                        threeP.setBackgroundResource(R.drawable.mark_progress_next);
                        break;
                    default:
                        break;
                }
                break;
            case R.id.iv_three:      // 身份录入
                switch (state) {
                    case PRE:
                        three.setBackgroundResource(R.drawable.mark_three_pre);
                        break;
                    case CURR:
                        three.setBackgroundResource(R.drawable.mark_three_curr);
                        break;
                    case NEXT:
                        three.setBackgroundResource(R.drawable.mark_three_next);
                        break;
                    default:
                        break;
                }
                break;
            case R.id.iv_four_p:        // 取卡p
                switch (state) {
                    case PRE:
                        fourP.setBackgroundResource(R.drawable.mark_progress_pre);
                        break;
                    case CURR:
                        fourP.setBackgroundResource(R.drawable.mark_progress_curr);
                        break;
                    case NEXT:
                        fourP.setBackgroundResource(R.drawable.mark_progress_next);
                        break;
                    default:
                        break;
                }
                break;
            case R.id.iv_four:          // 取卡
                switch (state) {
                    case PRE:
                        four.setBackgroundResource(R.drawable.mark_four_pre);
                        break;
                    case CURR:
                        four.setBackgroundResource(R.drawable.mark_four_curr);
                        break;
                    case NEXT:
                        four.setBackgroundResource(R.drawable.mark_four_next);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    /**--------------------------- 对外暴露的公共方法 ---------------------------*/

    public void showTomain() {
        mToHomeIv.setVisibility(View.VISIBLE);
    }

    public void hideTomain() {
        mToHomeIv.setVisibility(View.GONE);
    }

    /** 设置当前流程所在页数,页数只能为0 1 2 3 4*/
    public void setCurrentPage(int position) {
        if(position < 0 || position > 4) {
            return;
        }
        switch (position) {
            case 0:
                setImageState(R.id.iv_one, CURR);

                setImageState(R.id.iv_two_p, PRE);
                setImageState(R.id.iv_two, PRE);

                setImageState(R.id.iv_three_p, PRE);
                setImageState(R.id.iv_three, PRE);

                setImageState(R.id.iv_four_p, PRE);
                setImageState(R.id.iv_four, PRE);
                break;
            case 1:
                setImageState(R.id.iv_one, NEXT);

                setImageState(R.id.iv_two_p, CURR);
                setImageState(R.id.iv_two, CURR);

                setImageState(R.id.iv_three_p, PRE);
                setImageState(R.id.iv_three, PRE);

                setImageState(R.id.iv_four_p, PRE);
                setImageState(R.id.iv_four, PRE);
                break;
            case 2:
                setImageState(R.id.iv_one, NEXT);

                setImageState(R.id.iv_two_p, NEXT);
                setImageState(R.id.iv_two, NEXT);

                setImageState(R.id.iv_three_p, CURR);
                setImageState(R.id.iv_three, CURR);

                setImageState(R.id.iv_four_p, PRE);
                setImageState(R.id.iv_four, PRE);
                break;
            case 3:
                setImageState(R.id.iv_one, NEXT);

                setImageState(R.id.iv_two_p, NEXT);
                setImageState(R.id.iv_two, NEXT);

                setImageState(R.id.iv_three_p, NEXT);
                setImageState(R.id.iv_three, NEXT);

                setImageState(R.id.iv_four_p, CURR);
                setImageState(R.id.iv_four, CURR);
                break;
            case 4:
                setImageState(R.id.iv_one, NEXT);

                setImageState(R.id.iv_two_p, NEXT);
                setImageState(R.id.iv_two, NEXT);

                setImageState(R.id.iv_three_p, NEXT);
                setImageState(R.id.iv_three, NEXT);

                setImageState(R.id.iv_four_p, NEXT);
                setImageState(R.id.iv_four, NEXT);

                break;
            default:
                break;
        }
    }
}
