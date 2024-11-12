export const addCommaToFiltredInputValue = (inputValue: string, payload: any, trimValue: string) => {
    let newValue = inputValue;
    let openSearchBox = false;
  
    if (payload.valuType === "filtredValue") {
      if (!newValue.includes(payload.value)) {
        const lastCommaIndex = newValue.lastIndexOf(",");
        const beforeLastComma = newValue.substring(0, lastCommaIndex + 1);
        const afterLastComma = newValue.substring(lastCommaIndex + 1);
  
        const updatedAfterLastComma = afterLastComma.replace(trimValue, "");
        newValue = beforeLastComma + updatedAfterLastComma;
        newValue += payload.value;
        openSearchBox = true;
      } else {
        return { value: newValue, theSameUserInInput: true, openSearchBox };
      }
    } else {
      newValue = payload.value;
      openSearchBox = newValue.length > 0;
    }
  
    return { value: newValue, theSameUserInInput: false, openSearchBox };
  };