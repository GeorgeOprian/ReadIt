package com.example.readitapp.fragments;

import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.readitapp.R;
import com.example.readitapp.api.webserver.WebServerAPIBuilder;
import com.example.readitapp.model.webserver.SubscriptionDto;
import com.example.readitapp.model.webserver.book.request.BookRentRequestDto;
import com.example.readitapp.model.webserver.book.request.BookUserRequestDto;
import com.example.readitapp.model.webserver.book.response.BookDto;
import com.example.readitapp.model.webserver.book.response.BookListDto;
import com.example.readitapp.model.webserver.book.response.BookRentResponseDto;
import com.example.readitapp.model.webserver.book.response.CategoryDto;
import com.example.readitapp.utils.MySpannable;
import com.example.readitapp.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailsFragment extends Fragment {

    private View view;
    private ImageView poster;
    private TextView title;
    private TextView authors;
    private TextView category;
    private TextView description;
    private Button rentButton;
    private CheckBox wishlist;

    private BookDto bookDto;
    private boolean hasSubscription;
    public static final int MAX_LINES = 3;
    public static final String TWO_SPACES = " ";
    private boolean isExpanded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_book_details, container, false);

        initView();
        getSubscription();
        addBookDetails();

        rentButton.setOnClickListener(view -> rentBook());
        wishlist.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!bookDto.getInUserWishList()) {
                    addBookToWishlist();
                }
            } else {
                bookDto.setInUserWishList(false);
                removeBookFromWishlist();
            }
        });

        return view;
    }

    private void addBookDetails() {
        Bundle bundle = getArguments();

        Integer bookId = (Integer) bundle.get(Utils.BOOK_ID);

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Call<BookDto> call = WebServerAPIBuilder.getInstance().getBookById(bookId, email);

        call.enqueue(new Callback<BookDto>() {
            @Override
            public void onResponse(Call<BookDto> call, Response<BookDto> response) {
                if (response.isSuccessful()) {
                    bookDto = response.body();
                    fillBookView();
                }
            }

            @Override
            public void onFailure(Call<BookDto> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void removeBookFromWishlist() {
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Integer bookId = bookDto.getBookId();
        Call<List<BookListDto>> call = WebServerAPIBuilder.getInstance().removeBookFromWishList(bookId, email);
        call.enqueue(new Callback<List<BookListDto>>() {
            @Override
            public void onResponse(Call<List<BookListDto>> call, retrofit2.Response<List<BookListDto>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Book removed to wishlist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<BookListDto>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addBookToWishlist() {
        BookUserRequestDto bookUserRequestDto = new BookUserRequestDto();
        bookUserRequestDto.setUserEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        bookUserRequestDto.setBookId(bookDto.getBookId());
        Call<BookDto> call = WebServerAPIBuilder.getInstance().addBookToWishList(bookUserRequestDto);
        call.enqueue(new Callback<BookDto>() {
            @Override
            public void onResponse(Call<BookDto> call, retrofit2.Response<BookDto> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Book added to wishlist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BookDto> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void rentBook() {

        if (bookDto == null) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            return;
        }

        if (!hasSubscription) {
            Toast.makeText(getContext(), "No subscription", Toast.LENGTH_LONG).show();
            return;
        }

        if (Utils.currentUser.getAddressDto().isEmpty()) {
            Toast.makeText(getContext(), "Fill the address", Toast.LENGTH_LONG).show();
            return;
        }

        BookRentRequestDto bookRentRequestDto = getBookRentRequestDto();
        Call<BookRentResponseDto> call = WebServerAPIBuilder.getInstance().rentBook(bookRentRequestDto);
        call.enqueue(new Callback<BookRentResponseDto>() {
            @Override
            public void onResponse(Call<BookRentResponseDto> call, retrofit2.Response<BookRentResponseDto> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Book rented", Toast.LENGTH_LONG).show();
                } else if (response.code() == 404) {
                    Toast.makeText(getActivity(), "There are no volumes available in stock for this book. Sorry to inform you.", Toast.LENGTH_LONG).show();
                } else if (response.code() == 403) {
                    Toast.makeText(getActivity(), "You have already rented this book", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BookRentResponseDto> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @NotNull
    private BookRentRequestDto getBookRentRequestDto() {
        BookRentRequestDto bookRentRequestDto = new BookRentRequestDto();
        bookRentRequestDto.setBookId(bookDto.getBookId());
        bookRentRequestDto.setUserEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        LocalDate futureDate = LocalDate.now().plusMonths(1);
        bookRentRequestDto.setReturnDate(futureDate.toString());

        return bookRentRequestDto;
    }

    private void fillBookView() {
        if (bookDto.getThumbnail() != null) {
            Picasso.get().load(bookDto.getThumbnail().getSmallThumbnail()).into(poster);
        }

        title.setText(bookDto.getTitle());
        if (bookDto.getAuthor() != null && !bookDto.getAuthor().isEmpty()) {
            authors.setText(bookDto.getAuthor());
        } else {
            authors.setText("");
        }

        description.setText(bookDto.getDescription());
        makeTextViewResizable(description, 12, "See More", true);

        category.setText(bookDto.getCategories().stream().map(CategoryDto::getCategoryName).collect(Collectors.joining(",")));

        if (bookDto.getInUserWishList() != null) {
            wishlist.setChecked(bookDto.getInUserWishList());
        } else {
            wishlist.setChecked(false);
        }
    }

    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {


            ssb.setSpan(new MySpannable(false){
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, "See Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 12, ".. See More", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

    private void initView() {
        poster = view.findViewById(R.id.poster);
        title = view.findViewById(R.id.title);
        authors = view.findViewById(R.id.author_value);
        category = view.findViewById(R.id.category_value);
        description = view.findViewById(R.id.description_value);
        rentButton = view.findViewById(R.id.rent_button);
        wishlist = view.findViewById(R.id.wishlist);
    }

    private void getSubscription() {
        Call<SubscriptionDto> call = WebServerAPIBuilder.getInstance().getAvailability(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        call.enqueue(new Callback<SubscriptionDto>() {
            @Override
            public void onResponse(Call<SubscriptionDto> call, retrofit2.Response<SubscriptionDto> response) {
                if (response.isSuccessful()) {
                    hasSubscription = true;
                } else if (response.code() == 403) {
                    hasSubscription = false;
                }
            }

            @Override
            public void onFailure(Call<SubscriptionDto> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                hasSubscription = false;
            }
        });
    }
}