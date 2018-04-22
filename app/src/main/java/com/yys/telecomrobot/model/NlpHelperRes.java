package com.yys.telecomrobot.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yltang3 on 2017/9/1.
 */

public class NlpHelperRes {

    /**
     * interact_info : default -h 172.16.86.17 -p 20001
     * params : usr_id="",usr_org="1001",nbest="1",history_id="-2",username="unicom",proc_data_type="",rslt_encode="UTF-8",enable_debug="false",optim_info_out="false",suggest_rule="false",aim_knowledgepoint="查询宽带覆盖",encode="UTF-8",rslt_format="JSON",only_fill_slot="false",proc_data_type="",knowledge_point="查询宽带覆盖",session_end="true",proc_data_type="",res_node_lst="1001",res_parse_mod="sb"
     * result : {"matchInfo":{"confidence":"acceptable","extraInfo":{"DocSemanticInfo":" 查询_@查询<业务> 宽带覆盖_@宽带覆盖<业务>","EX_FLAG":"0","STANDARD_QUESTION":"查询宽带覆盖","SearchSemanticInfo":" 宽带覆盖_@宽带覆盖<业务>","doc_id":"9227_0","history_id":"0","hypernym_str":"","mapping_id":"","match_formular":"查询宽带覆盖","match_pair":"<宽带覆盖,宽带覆盖> ","matched_rules":"","org":"1001","search_formular":"( 0.330)查询宽带覆盖 && ( 0.797)syntax: 0.000查询宽带覆盖 && tbcnn: ( 0.000)"},"id":"64","questionStd":"查询宽带覆盖","score":" 0.464"},"operation":"query","rc":0,"semantic":{"slots":{"action":{"name":"查询"},"target":{"name":"宽带覆盖","params":{"params1":"default"}}}},"service":"default","text":"宽带覆盖"}
     * return : 0
     * sensitive :
     */

    private String interact_info;
    private String params;
    private ResultBean result;
    @SerializedName("return")
    private int returnX;
    private String sensitive;

    public String getInteract_info() {
        return interact_info;
    }

    public void setInteract_info(String interact_info) {
        this.interact_info = interact_info;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getReturnX() {
        return returnX;
    }

    public void setReturnX(int returnX) {
        this.returnX = returnX;
    }

    public String getSensitive() {
        return sensitive;
    }

    public void setSensitive(String sensitive) {
        this.sensitive = sensitive;
    }

    public static class ResultBean {
        /**
         * matchInfo : {"confidence":"acceptable","extraInfo":{"DocSemanticInfo":" 查询_@查询<业务> 宽带覆盖_@宽带覆盖<业务>","EX_FLAG":"0","STANDARD_QUESTION":"查询宽带覆盖","SearchSemanticInfo":" 宽带覆盖_@宽带覆盖<业务>","doc_id":"9227_0","history_id":"0","hypernym_str":"","mapping_id":"","match_formular":"查询宽带覆盖","match_pair":"<宽带覆盖,宽带覆盖> ","matched_rules":"","org":"1001","search_formular":"( 0.330)查询宽带覆盖 && ( 0.797)syntax: 0.000查询宽带覆盖 && tbcnn: ( 0.000)"},"id":"64","questionStd":"查询宽带覆盖","score":" 0.464"}
         * operation : query
         * rc : 0
         * semantic : {"slots":{"action":{"name":"查询"},"target":{"name":"宽带覆盖","params":{"params1":"default"}}}}
         * service : default
         * text : 宽带覆盖
         */

        private MatchInfoBean matchInfo;
        private String operation;
        private int rc;
        private SemanticBean semantic;
        private String service;
        private String text;

        public MatchInfoBean getMatchInfo() {
            return matchInfo;
        }

        public void setMatchInfo(MatchInfoBean matchInfo) {
            this.matchInfo = matchInfo;
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public int getRc() {
            return rc;
        }

        public void setRc(int rc) {
            this.rc = rc;
        }

        public SemanticBean getSemantic() {
            return semantic;
        }

        public void setSemantic(SemanticBean semantic) {
            this.semantic = semantic;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public static class MatchInfoBean {
            /**
             * confidence : acceptable
             * extraInfo : {"DocSemanticInfo":" 查询_@查询<业务> 宽带覆盖_@宽带覆盖<业务>","EX_FLAG":"0","STANDARD_QUESTION":"查询宽带覆盖","SearchSemanticInfo":" 宽带覆盖_@宽带覆盖<业务>","doc_id":"9227_0","history_id":"0","hypernym_str":"","mapping_id":"","match_formular":"查询宽带覆盖","match_pair":"<宽带覆盖,宽带覆盖> ","matched_rules":"","org":"1001","search_formular":"( 0.330)查询宽带覆盖 && ( 0.797)syntax: 0.000查询宽带覆盖 && tbcnn: ( 0.000)"}
             * id : 64
             * questionStd : 查询宽带覆盖
             * score :  0.464
             */

            private String confidence;
            private ExtraInfoBean extraInfo;
            private String id;
            private String questionStd;
            private String score;

            public String getConfidence() {
                return confidence;
            }

            public void setConfidence(String confidence) {
                this.confidence = confidence;
            }

            public ExtraInfoBean getExtraInfo() {
                return extraInfo;
            }

            public void setExtraInfo(ExtraInfoBean extraInfo) {
                this.extraInfo = extraInfo;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getQuestionStd() {
                return questionStd;
            }

            public void setQuestionStd(String questionStd) {
                this.questionStd = questionStd;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public static class ExtraInfoBean {
                /**
                 * DocSemanticInfo :  查询_@查询<业务> 宽带覆盖_@宽带覆盖<业务>
                 * EX_FLAG : 0
                 * STANDARD_QUESTION : 查询宽带覆盖
                 * SearchSemanticInfo :  宽带覆盖_@宽带覆盖<业务>
                 * doc_id : 9227_0
                 * history_id : 0
                 * hypernym_str :
                 * mapping_id :
                 * match_formular : 查询宽带覆盖
                 * match_pair : <宽带覆盖,宽带覆盖>
                 * matched_rules :
                 * org : 1001
                 * search_formular : ( 0.330)查询宽带覆盖 && ( 0.797)syntax: 0.000查询宽带覆盖 && tbcnn: ( 0.000)
                 */

                private String DocSemanticInfo;
                private String EX_FLAG;
                private String STANDARD_QUESTION;
                private String SearchSemanticInfo;
                private String doc_id;
                private String history_id;
                private String hypernym_str;
                private String mapping_id;
                private String match_formular;
                private String match_pair;
                private String matched_rules;
                private String org;
                private String search_formular;

                public String getDocSemanticInfo() {
                    return DocSemanticInfo;
                }

                public void setDocSemanticInfo(String DocSemanticInfo) {
                    this.DocSemanticInfo = DocSemanticInfo;
                }

                public String getEX_FLAG() {
                    return EX_FLAG;
                }

                public void setEX_FLAG(String EX_FLAG) {
                    this.EX_FLAG = EX_FLAG;
                }

                public String getSTANDARD_QUESTION() {
                    return STANDARD_QUESTION;
                }

                public void setSTANDARD_QUESTION(String STANDARD_QUESTION) {
                    this.STANDARD_QUESTION = STANDARD_QUESTION;
                }

                public String getSearchSemanticInfo() {
                    return SearchSemanticInfo;
                }

                public void setSearchSemanticInfo(String SearchSemanticInfo) {
                    this.SearchSemanticInfo = SearchSemanticInfo;
                }

                public String getDoc_id() {
                    return doc_id;
                }

                public void setDoc_id(String doc_id) {
                    this.doc_id = doc_id;
                }

                public String getHistory_id() {
                    return history_id;
                }

                public void setHistory_id(String history_id) {
                    this.history_id = history_id;
                }

                public String getHypernym_str() {
                    return hypernym_str;
                }

                public void setHypernym_str(String hypernym_str) {
                    this.hypernym_str = hypernym_str;
                }

                public String getMapping_id() {
                    return mapping_id;
                }

                public void setMapping_id(String mapping_id) {
                    this.mapping_id = mapping_id;
                }

                public String getMatch_formular() {
                    return match_formular;
                }

                public void setMatch_formular(String match_formular) {
                    this.match_formular = match_formular;
                }

                public String getMatch_pair() {
                    return match_pair;
                }

                public void setMatch_pair(String match_pair) {
                    this.match_pair = match_pair;
                }

                public String getMatched_rules() {
                    return matched_rules;
                }

                public void setMatched_rules(String matched_rules) {
                    this.matched_rules = matched_rules;
                }

                public String getOrg() {
                    return org;
                }

                public void setOrg(String org) {
                    this.org = org;
                }

                public String getSearch_formular() {
                    return search_formular;
                }

                public void setSearch_formular(String search_formular) {
                    this.search_formular = search_formular;
                }
            }
        }

        public static class SemanticBean {
            /**
             * slots : {"action":{"name":"查询"},"target":{"name":"宽带覆盖","params":{"params1":"default"}}}
             */

            private SlotsBean slots;

            public SlotsBean getSlots() {
                return slots;
            }

            public void setSlots(SlotsBean slots) {
                this.slots = slots;
            }

            public static class SlotsBean {
                /**
                 * action : {"name":"查询"}
                 * target : {"name":"宽带覆盖","params":{"params1":"default"}}
                 */

                private ActionBean action;
                private TargetBean target;

                public ActionBean getAction() {
                    return action;
                }

                public void setAction(ActionBean action) {
                    this.action = action;
                }

                public TargetBean getTarget() {
                    return target;
                }

                public void setTarget(TargetBean target) {
                    this.target = target;
                }

                public static class ActionBean {
                    /**
                     * name : 查询
                     */

                    private String name;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }

                public static class TargetBean {
                    /**
                     * name : 宽带覆盖
                     * params : {"params1":"default"}
                     */

                    private String name;
                    private ParamsBean params;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public ParamsBean getParams() {
                        return params;
                    }

                    public void setParams(ParamsBean params) {
                        this.params = params;
                    }

                    public static class ParamsBean {
                        /**
                         * params1 : default
                         */

                        private String params1;

                        public String getParams1() {
                            return params1;
                        }

                        public void setParams1(String params1) {
                            this.params1 = params1;
                        }
                    }
                }
            }
        }
    }
}
