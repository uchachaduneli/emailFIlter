function caseRequredFields(formVar) {
    if (formVar.combined.$error.required) {
        errorMsg('Combined Is Mandatory Field!');
        return false;
    }
    if (formVar.contactEmail.$error.required) {
        errorMsg('Contact Email Is Mandatory Field!');
        return false;
    }
    if (formVar.daysCount.$error.required) {
        errorMsg('Days Count Is Mandatory Field!');
        return false;
    }
    if (formVar.nightsCount.$error.required) {
        errorMsg('Nights Count Is Mandatory Field!');
        return false;
    }
    if (formVar.touristCount.$error.required) {
        errorMsg('Tourists Count Is Mandatory Field!');
        return false;
    }
    if (formVar.arrivalCityId.$error.required) {
        errorMsg('Arrival City Is Mandatory Field');
        return false;
    }
    if (formVar.leaveCityId.$error.required) {
        errorMsg('Leave City Is Mandatory Field');
        return false;
    }
    if (formVar.guideDriver.$error.required) {
        errorMsg('Guide/Driver Is Mandatory Field');
        return false;
    }
    if (formVar.guideLanguageId.$error.required) {
        errorMsg('Guide Language Is Mandatory Field');
        return false;
    }
    if (formVar.mealCategoryId.$error.required) {
        errorMsg('Meal Category Is Mandatory Field');
        return false;
    }
    if (formVar.entranceFees.$error.required) {
        errorMsg(' Entrance Fees Is Mandatory Field');
        return false;
    }
    if (formVar.nationality.$error.required) {
        errorMsg(' Nationality Is Mandatory Field');
        return false;
    }
    if (formVar.currencyId.$error.required) {
        errorMsg(' Currency Is Mandatory Field');
        return false;
    }
    if (formVar.packageCategoryId.$error.required) {
        errorMsg(' Package Category Is Mandatory Field');
        return false;
    }
    if (formVar.languageGroupId.$error.required) {
        errorMsg('Below Language Group Is Mandatory Field');
        return false;
    }
    return true;
}