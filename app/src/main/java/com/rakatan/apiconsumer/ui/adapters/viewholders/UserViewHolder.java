package com.rakatan.apiconsumer.ui.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rakatan.apiconsumer.R;
import com.rakatan.apiconsumer.models.User;
import com.rakatan.apiconsumer.ui.adapters.UsersAdapter;

import org.joda.time.DateTime;
import org.joda.time.Years;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserViewHolder extends RecyclerView.ViewHolder {

    int layout = R.layout.item_user;
    @BindView(R.id.imageThumb)
    CircleImageView imageThumb;
    @BindView(R.id.textThumb)
    TextView textThumb;
    @BindView(R.id.layoutThumbnails)
    RelativeLayout layoutThumbnails;
    @BindView(R.id.textUserName)
    TextView textUserName;
    @BindView(R.id.textUserDetails)
    TextView textUserDetails;
    @BindView(R.id.imageAttachment)
    ImageView imageAttachment;
    @BindView(R.id.textTime)
    TextView textTime;
    @BindView(R.id.imageStarred)
    ImageView imageStarred;
    @BindView(R.id.layoutInfo)
    LinearLayout layoutInfo;
    @BindView(R.id.root)
    RelativeLayout root;
    private UsersAdapter.Inputs inputs;

    public UserViewHolder(View itemView, UsersAdapter.Inputs inputs) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.inputs = inputs;
    }

    public void bind(User user, Context context) {
        root.setOnClickListener(v -> inputs.userClicked(user));

        if (!TextUtils.isEmpty(user.getPicture().getThumbnail())) {
            useImageThumb();
            Glide.with(context)
                    .load(user.getPicture().getThumbnail())
                    .into(imageThumb);
        } else {
            useTextThumb();
            textThumb.setText(user.getName().getFirst().charAt(0));
        }

        textUserName.setText(user.getName().getTitle() + " "
                + user.getName().getFirst() + " "
                + user.getName().getLast());

        fillinUserDetails(user);

        fillInTime(user);

        fillInInfo(user);
    }

    private void fillinUserDetails(User user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTime today = new DateTime();
        String userYears = "N/A";
        try {
            DateTime dob = new DateTime(sdf.parse(user.getDob()));
            userYears = Integer.toString(Years.yearsBetween(dob, today).getYears());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        textUserDetails.setText(userYears + " years from " + countryCodeToFlag(user.getNat()));
    }

    private void fillInTime(User user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createdHour = "N/A";
        try {
            DateTime created = new DateTime(sdf.parse(user.getRegistered()));
            SimpleDateFormat sdfHours = new SimpleDateFormat("HH:mm");
            createdHour = sdfHours.format(created.toDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        textTime.setText(createdHour);
    }

    private void fillInInfo(User user) {
        switch (user.getGender()) {
            case "female":
                imageAttachment.setVisibility(View.GONE);
                imageStarred.setImageResource(R.drawable.ic_star_fill);
                break;
            case "male":
                imageAttachment.setVisibility(View.VISIBLE);
                imageStarred.setImageResource(R.drawable.ic_star_border_grey);
                break;
            default:
                imageAttachment.setVisibility(View.GONE);
                imageStarred.setImageResource(R.drawable.ic_star_border_grey);
                break;
        }
    }
    private void useImageThumb() {
        textThumb.setVisibility(View.GONE);
        imageThumb.setVisibility(View.VISIBLE);
    }

    private void useTextThumb() {
        imageThumb.setVisibility(View.GONE);
        textThumb.setVisibility(View.VISIBLE);
    }

    private String countryCodeToFlag(String countryCode) {
        if (countryCode.length() != 2) {
            return "N/A";
        }
        int flagOffset = 0x1F1E6;
        int asciiOffset = 0x41;

        int firstChar = Character.codePointAt(countryCode, 0) - asciiOffset + flagOffset;
        int secondChar = Character.codePointAt(countryCode, 1) - asciiOffset + flagOffset;

        return new String(Character.toChars(firstChar))
                + new String(Character.toChars(secondChar));
    }
}
