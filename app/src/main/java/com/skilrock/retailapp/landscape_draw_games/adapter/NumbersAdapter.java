package com.skilrock.retailapp.landscape_draw_games.adapter;

public class NumbersAdapter {//extends RecyclerView.Adapter<NumbersAdapter.GameViewHolder> implements AppConstants {
 //extends RecyclerView.Adapter<NumbersAdapter.GameViewHolder> implements AppConstants
    /*private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball> listNumber;
    private Activity context;
    private BallClickListener listener;
    private ArrayList<String> listQpNumbers;
    private int recyclerViewHeight, numberOfRows;
    private int cellHeight;
    private long mLastClickTime = 0;
    private int maxNumberSelection = 0;
    private String gameCode;

    public NumbersAdapter(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball> listNumber, Activity context, BallClickListener listener, ArrayList<String> listQpNumbers,
                          int recyclerViewHeight, int numberOfRows, int cellHeight, int maxNumberSelection, String gameCode) {
        this.listNumber         = listNumber;
        this.context            = context;
        this.listener           = listener;
        this.listQpNumbers      = listQpNumbers;
        this.recyclerViewHeight = recyclerViewHeight;
        this.numberOfRows       = numberOfRows;
        this.cellHeight         = cellHeight;
        this.maxNumberSelection = maxNumberSelection;
        this.gameCode = gameCode;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;

        if (gameCode.equalsIgnoreCase(LUCKY_SIX))
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_lucky_six_number, viewGroup, false);
        else
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_five_by_ninety_numbers, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball ball = listNumber.get(position);
        holder.tvNumber.setText(ball.getNumber());
        holder.tvNumber.setTag(ball.getColor());
        holder.imageBlur.setVisibility(View.GONE);

        holder.tvNumber.setBackgroundResource(R.drawable.five_by_ninety_numbers);
        GradientDrawable background = (GradientDrawable) holder.tvNumber.getBackground();

        *//*background.setColor(!listener.getSelectedList().contains(ball.getNumber()) ? Color.parseColor("#FFFFFF") : Color.parseColor(FiveByNinetyColors.getBallColor(ball.getColor())));
        holder.tvNumber.setTextColor(!listener.getSelectedList().contains(ball.getNumber()) ? context.getResources().getColor(R.color.colorBlack) :
                context.getResources().getColor(R.color.colorWhite));

        if (listener.getSelectedList().contains(ball.getNumber()))
            holder.tvNumber.setTypeface(holder.tvNumber.getTypeface(), Typeface.BOLD);
        else
            holder.tvNumber.setTypeface(holder.tvNumber.getTypeface(), Typeface.NORMAL);*//*

        if (listQpNumbers != null && !listQpNumbers.isEmpty()) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                background.setColor(listQpNumbers.contains(ball.getNumber()) ? Color.parseColor(FiveByNinetyColors.getBallColor(ball.getColor())) : Color.parseColor("#FFFFFF"));
            } else {
                if (listQpNumbers.contains(ball.getNumber())) {
                    Utils.animateColor(holder.tvNumber, Color.parseColor("#FFFFFF"), Color.parseColor(FiveByNinetyColors.getBallColor(ball.getColor())), context);
                } else {
                    background.setColor(Color.parseColor("#FFFFFF"));
                }
            }

            if (listQpNumbers.contains(ball.getNumber()))
                holder.tvNumber.setTypeface(holder.tvNumber.getTypeface(), Typeface.BOLD);
            else
                holder.tvNumber.setTypeface(holder.tvNumber.getTypeface(), Typeface.NORMAL);

            holder.tvNumber.setClickable(false);
            holder.tvNumber.setFocusable(false);
            holder.tvNumber.setEnabled(false);
        } else {
            holder.tvNumber.setClickable(true);
            holder.tvNumber.setFocusable(true);
            holder.tvNumber.setEnabled(true);
        }

        if (listener.getBankerList() != null && !listener.getBankerList().isEmpty() && listener.getBankerList().contains(ball.getNumber())) {
            holder.imageBlur.setVisibility(View.VISIBLE);
        }

        holder.tvNumber.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 200) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();

            if (listener.getSelectedList().size() < maxNumberSelection && !listener.getSelectedList().contains(ball.getNumber())) {
                if (listener.getSelectedList().contains(ball.getNumber()))
                    holder.tvNumber.setTypeface(null, Typeface.NORMAL);
                else
                    holder.tvNumber.setTypeface(null, Typeface.BOLD);

                holder.tvNumber.setTextColor(listener.getSelectedList().contains(ball.getNumber()) ? context.getResources().getColor(R.color.colorBlack) :
                        context.getResources().getColor(R.color.colorWhite));
            }

            listener.onBallClicked(holder.tvNumber, ball.getNumber(), FiveByNinetyColors.getBallColor(ball.getColor()), ball);
        });
    }

    @Override
    public int getItemCount() {
        return listNumber.size();
    }

    class GameViewHolder extends RecyclerView.ViewHolder {

        TextView tvNumber;
        ImageView imageBlur;

        GameViewHolder(@NonNull View view) {
            super(view);
            setIsRecyclable(false);

            tvNumber = view.findViewById(R.id.tvNumber);
            imageBlur = view.findViewById(R.id.image_blur);
        }
    }*/
}