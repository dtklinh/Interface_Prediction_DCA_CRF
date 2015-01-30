/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BLAST;



/**
 *
 * @author linh
 */
public class AlignedBlock {
    private int query_start;
    private int query_end;
    private String query_str;
    private String subject_str;

    /**
     * @return the query_start
     */
    public int getQuery_start() {
        return query_start;
    }

    /**
     * @param query_start the query_start to set
     */
    public void setQuery_start(int query_start) {
        this.query_start = query_start;
    }

    /**
     * @return the query_end
     */
    public int getQuery_end() {
        return query_end;
    }

    /**
     * @param query_end the query_end to set
     */
    public void setQuery_end(int query_end) {
        this.query_end = query_end;
    }

    /**
     * @return the query_str
     */
    public String getQuery_str() {
        return query_str;
    }

    /**
     * @param query_str the query_str to set
     */
    public void setQuery_str(String query_str) {
        this.query_str = query_str;
    }

    /**
     * @return the subject_str
     */
    public String getSubject_str() {
        return subject_str;
    }

    /**
     * @param subject_str the subject_str to set
     */
    public void setSubject_str(String subject_str) {
        this.subject_str = subject_str;
    }
    public AlignedBlock(){
        this.query_start = 100000;
        this.query_end = -10000;
    }
    public AlignedBlock(int begin, int end, String query, String subject){
        this.query_start = begin;
        this.query_end = end;
        this.query_str = query;
        this.subject_str = subject;
    }
    public void AdjustGapInQuery(){
        while(query_str.indexOf("-")>=0){
            int index = query_str.indexOf("-");
            StringBuilder sb_query = new StringBuilder(query_str);
            StringBuilder sb_sbj = new StringBuilder(subject_str);
            sb_query.deleteCharAt(index);
            sb_sbj.deleteCharAt(index);
            this.query_str = sb_query.toString();
            this.subject_str = sb_sbj.toString();
        }
    }
    public void EliminateGap(){
        if(query_str.length() != subject_str.length()){
            System.err.println("query and subject not same length");
            System.exit(1);
        }
        if(this.query_str.indexOf("-")>=0){
            int len = this.query_str.length();
            StringBuilder qb = new StringBuilder(query_str);
            StringBuilder sb = new StringBuilder(subject_str);
            for(int i=len-1;i>=0;i--){
                if(qb.substring(i, i+1).equalsIgnoreCase("-")){
                    qb.deleteCharAt(i);
                    sb.deleteCharAt(i);
                }
            }
            this.query_str = qb.toString();
            this.subject_str = sb.toString();
        }
    }
}
