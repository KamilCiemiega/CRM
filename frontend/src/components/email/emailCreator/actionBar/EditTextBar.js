import { useState } from "react";
import { useDispatch } from "react-redux";
import "../../../../style/EditTexBar.css";
import {
  Undo,
  Redo,
  FormatBold,
  FormatItalic,
  FormatUnderlined,
  FormatListBulleted,
  FormatListNumbered,
} from "@mui/icons-material";
import { editTextAction } from "../../../store/editText-slice";

const iconStyle = {
  marginRight: "4px",
  cursor: "pointer",
};
const activeIconStyle = {
  ...iconStyle,
  backgroundColor: "rgba(32,33,36,0.122)",
  borderRadius: "3px",
};

const EditTextBar = () => {
  const [activeIcon, setActiveIcon] = useState(" ");
  const [showFontSize, setShowFontSize] = useState(false);
  const dispatch = useDispatch();

  const handleClick = (iconType) => {
    switch (iconType) {
      case "FormatBold":
        setActiveIcon("FormatBold");
        dispatch(editTextAction.setBoldText());
        break;
      case "FormatItalic":
        setActiveIcon("FormatItalic");
        dispatch(editTextAction.setItlicText());
        break;
      case "FormatUnderlined":
        setActiveIcon("FormatUnderlined");
        dispatch(editTextAction.setUnderlineText());
        break;
      case "Undo":
        setActiveIcon("Undo");
        dispatch(editTextAction.setUndo());
        break;
      case "Redo":
        setActiveIcon("Redo");
        dispatch(editTextAction.setRedo());
        break;
      case "FormatSize":
        setShowFontSize(!showFontSize);
        break;
      case "FormatListBulleted":
        setActiveIcon("FormatListBulleted");
        dispatch(editTextAction.setBulletList());
        break;
      case "FormatListNumbered":
        setActiveIcon("FormatListNumbered");
        dispatch(editTextAction.setNumberedList());
        break;
      default:
        return null;
    }
  };

  return (
    <div id="container">
      <Undo
        sx={activeIcon === "Undo" ? activeIconStyle : iconStyle}
        onClick={() => handleClick("Undo")}
      />
      <Redo
        sx={activeIcon === "Redo" ? activeIconStyle : iconStyle}
        onClick={() => handleClick("Redo")}
      />
      <FormatBold
        sx={activeIcon === "FormatBold" ? activeIconStyle : iconStyle}
        onClick={() => handleClick("FormatBold")}
      />
      <FormatItalic
        sx={activeIcon === "FormatItalic" ? activeIconStyle : iconStyle}
        onClick={() => handleClick("FormatItalic")}
      />
      <FormatUnderlined
        sx={activeIcon === "FormatUnderlined" ? activeIconStyle : iconStyle}
        onClick={() => handleClick("FormatUnderlined")}
      />
      <FormatListBulleted
        sx={activeIcon === "FormatListBulleted" ? activeIconStyle : iconStyle}
        onClick={() => handleClick("FormatListBulleted")}
      />
      <FormatListNumbered
        sx={activeIcon === "FormatListNumbered" ? activeIconStyle : iconStyle}
        onClick={() => handleClick("FormatListNumbered")}
      />
    </div>
  );
};

export default EditTextBar;
