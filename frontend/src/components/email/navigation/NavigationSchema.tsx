import { ReactElement } from "react";
import {
    Inbox,
    Drafts,
    Send,
    Email,
    Replay,
    People,
    ForwardToInbox,
    Delete,
    ModeEdit,
    Star,
    Restore,
    Clear,
    DeleteForever,
    StarOutline
  } from "@mui/icons-material";

  interface NavigationItem {
    icon: ReactElement;
    primary: string;
    index: number;
    collapseItems: CollapseItem[];
  }
  
  interface CollapseItem {
    icon: ReactElement;
    primary: string;
    index: number;
  }


const navigationData: NavigationItem[] = [
    { 
      icon: <Inbox />, 
      primary: "Inbox", 
      index: 1, 
      collapseItems: [
        { icon: <Email />, primary: "New message", index: 2 },
        { icon: <Replay />, primary: "Replay", index: 3 },
        { icon: <People />, primary: "Replay to All", index: 4 },
        { icon: <ForwardToInbox />, primary: "Forward", index: 5 },
        { icon: <Star />, primary: "Favorite", index: 6 },
        { icon: <Delete />, primary: "Delete", index: 7 }
      ]
    },
    { 
      icon: <Send />, 
      primary: "Sent", 
      index: 8, 
      collapseItems: [
        { icon: <ForwardToInbox />, primary: "Forward", index: 9 },
        { icon: <Star />, primary: "favorite", index: 10 },
        { icon: <Delete />, primary: "Delete", index: 11 }
      ]
    },
    { 
      icon: <Drafts />, 
      primary: "Drafts", 
      index: 12, 
      collapseItems: [
        { icon: <Email />, primary: "New draft", index: 13 },
        { icon: <ModeEdit />, primary: "Edit", index: 14 },
        { icon: <ForwardToInbox />, primary: "Forward", index: 15 },
        { icon: <Delete />, primary: "Delete", index: 16 }
      ]
    },
    { 
      icon: <Star />, 
      primary: "Favorite", 
      index: 17, 
      collapseItems: [
        { icon: <Replay />, primary: "Replay", index: 18 },
        { icon: <People />, primary: "Replay to all", index: 19 },
        { icon: <ForwardToInbox />, primary: "Forward", index: 20 },
        { icon: <StarOutline />, primary: "unFavorite", index: 21 }
      ]
    },
    { 
      icon: <Delete />, 
      primary: "Trash", 
      index: 22, 
      collapseItems: [
        { icon: <Restore />, primary: "Restore", index: 23 },
        { icon: <Clear />, primary: "Remove", index: 24 },
        { icon: <DeleteForever />, primary: "Remove All", index: 25 },
      ]
    }
  ];

  export default navigationData;