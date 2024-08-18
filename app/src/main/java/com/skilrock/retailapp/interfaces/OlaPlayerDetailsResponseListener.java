package com.skilrock.retailapp.interfaces;

import com.skilrock.retailapp.models.ola.OlaPlayerDetailsResponseBean;

public interface OlaPlayerDetailsResponseListener {

    void onResponse(OlaPlayerDetailsResponseBean response);
    void onLoadMoreResponse(OlaPlayerDetailsResponseBean response);

}
