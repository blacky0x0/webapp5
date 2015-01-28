package ru.javawebinar.webapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * GKislin
 * 19.12.2014.
 */
public class Organization implements Serializable {
    static final long serialVersionUID = 1L;

    private Link link;
    private List<Period> periods;

    public Organization() {
    }

    public Organization(Link link, List<Period> periods) {
        this.link = link;
        this.periods = periods;
    }

    public static class Period {
        private Date startDate;
        private Date endDate;
        private String position;
        private String content;

        public Period() {
        }

        public Period(Date startDate, Date endDate, String position, String content) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.position = position;
            this.content = content;
//            link.getName();
        }
    }
}
