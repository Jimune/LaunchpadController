package com.jimune.controller.page;

import com.jimune.controller.page.actions.first.DirSelector;
import com.jimune.controller.page.actions.first.PrntScrnPrivateAction;
import com.jimune.controller.page.actions.first.PrntScrnPublicAction;
import com.jimune.controller.page.actions.first.RightClickAction;
import com.jimune.controller.page.actions.settings.*;
import com.jimune.controller.page.actions.speed.RightClickHoldAction;
import com.jimune.controller.page.actions.speed.ShiftSpamAction;
import com.jimune.controller.util.Utils;

import java.util.ArrayList;

public class LaunchPageHandler {

    public static LaunchPage curPage = null;
    public static LaunchPage settings = null;
    public static int totalPages = 0;
    public static ArrayList<LaunchPage> pages = new ArrayList<LaunchPage>();

    public static void addPage(LaunchPage page) {
        LaunchPage p = getPage(page);

        if (p == null) {
            pages.add(page);
            totalPages = pages.size();
            page.thisPage = totalPages;
            if (totalPages == 1) {
                curPage = page;
            }
        }
    }

    public static LaunchPage getPage(LaunchPage page) {
        for (LaunchPage p : pages) {
            if (p.equals(page)) {
                return p;
            }
        }

        return null;
    }

    public static LaunchPage getPage(int page) {
        for (LaunchPage p : pages) {
            if (page == p.thisPage) {
                return p;
            }
        }

        return null;
    }

    public static int previousPage() {
        if (curPage.thisPage - 1 > 0) {
            LaunchPage nextPage = getPage(curPage.thisPage - 1);
            curPage = nextPage;
            Utils.setPadFromPage(nextPage);
            return nextPage.thisPage;
        }

        return curPage.thisPage;
    }

    public static int nextPage() {
        if (curPage.thisPage + 1 <= totalPages) {
            LaunchPage nextPage = getPage(curPage.thisPage + 1);
            curPage = nextPage;
            Utils.setPadFromPage(nextPage);
            return nextPage.thisPage;
        }

        return curPage.thisPage;
    }

    public static void setupSettings() {
        settings = new LaunchPage(
                new RunnableUp(),
                new RunnableDown(),
                new TimeoutUp(),
                new TimeoutDown(),
                new ShowVars()
        );
        settings.thisPage = 9999;
    }

    public static void destroyPages() {
        curPage = null;
        totalPages = 0;
        pages.clear();
    }

    public static void createPages() {
        addPage(new LaunchPage( // Main page
                        new PrntScrnPrivateAction(),
                        new PrntScrnPublicAction(),
                        new RightClickAction(),
                        new DirSelector()
                )
        );
        addPage(new LaunchPage( // Utils page
                        new RightClickHoldAction(),
                        new ShiftSpamAction()
                )
        );
        addPage(new LaunchPage()); // Blank page for copypaste function - Page 3

        Utils.addCopyPaste(3, 0);
        Utils.addCopyPaste(3, 1);
        Utils.addCopyPaste(3, 2);

        Utils.setPadFromPage(curPage);
    }
}
