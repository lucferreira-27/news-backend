package com.example.newsbackend.service.nlu.watson;

import com.example.newsbackend.service.nlu.AnalyseResult;
import com.google.gson.annotations.SerializedName;
import com.ibm.watson.natural_language_understanding.v1.model.*;

import java.util.List;

public class WatsonAnalyseResult extends AnalysisResults implements AnalyseResult {
    protected List<ConceptsResult> concepts;
    protected List<EntitiesResult> entities;
    protected List<KeywordsResult> keywords;
    protected List<CategoriesResult> categories;
    protected List<ClassificationsResult> classifications;
    protected EmotionResult emotion;
    protected FeaturesResultsMetadata metadata;
    protected List<RelationsResult> relations;
    @SerializedName("semantic_roles")
    protected List<SemanticRolesResult> semanticRoles;
    protected SentimentResult sentiment;
    protected SyntaxResult syntax;
}
