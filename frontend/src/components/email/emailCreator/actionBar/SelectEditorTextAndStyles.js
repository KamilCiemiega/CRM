import { convertToRaw } from "draft-js";


export const selectEditorTextAndStyles = (editorState) => {
    const content = editorState.getCurrentContent();
    const rawContent = convertToRaw(content);
    let styledText = [];

    rawContent.blocks.forEach(block => {
        let blockText = block.text;
        let lastOffset = 0;

        block.inlineStyleRanges.forEach(styleRange => {
            if (styleRange.offset > lastOffset) {
                styledText.push({
                    text: blockText.slice(lastOffset, styleRange.offset),
                    styles: []
                });
            }
      
            styledText.push({
                text: blockText.slice(styleRange.offset, styleRange.offset + styleRange.length),
                styles: [styleRange.style]
            });
            lastOffset = styleRange.offset + styleRange.length;
        });

        if (lastOffset < blockText.length) {
            styledText.push({
                text: blockText.slice(lastOffset),
                styles: []
            });
        }
    });

    return styledText;
}