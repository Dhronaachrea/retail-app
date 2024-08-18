<html>
 <body>
 <script type="text/javascript" async>
        var _ic = _ic || [];
                        _ic.push(['gametype', 'sbs']);
                        _ic.push(['type', 'retailerPos']);
                        _ic.push(['player_id', Android.getPlayerId()]);
                        _ic.push(['player_name', Android.getPlayerName()]);
                        _ic.push(['session_id', Android.getSessionId()]);
                        _ic.push(['balance', Android.getBalnce()]);
                        _ic.push(['language', Android.getLanguage()]);
                        _ic.push(['currency', 'THB']);
                        _ic.push(['server', Android.getServer()]);
                        _ic.push(['alias', '_noAlias']);
                        _ic.push(['iframe_div_id', 'lottogames_div_iframe']);

            (function(){
                document.write('<' + 'script type="text/javascript" src="'+Android.getServer()+'assets/js/lottogames.js?"><'+'/script>');
            })();

     function callJS()
     {
            LottoGames.frame(_ic);
     }



    </script>
</body>
</html>