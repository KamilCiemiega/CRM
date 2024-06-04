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
    DeleteForever
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
        { icon: <Delete />, primary: "Delete", index: 6 }
      ]
    },
    { 
      icon: <Send />, 
      primary: "Sent", 
      index: 7, 
      collapseItems: [
        { icon: <ForwardToInbox />, primary: "Forward", index: 8 },
        { icon: <Delete />, primary: "Delete", index: 9 }
      ]
    },
    { 
      icon: <Drafts />, 
      primary: "Drafts", 
      index: 10, 
      collapseItems: [
        { icon: <Email />, primary: "New draft", index: 11 },
        { icon: <ModeEdit />, primary: "Edit", index: 12 },
        { icon: <ForwardToInbox />, primary: "Forward", index: 13 },
        { icon: <Delete />, primary: "Delete", index: 14 }
      ]
    },
    { 
      icon: <Star />, 
      primary: "Follow Up", 
      index: 15, 
      collapseItems: [
        { icon: <Replay />, primary: "Replay", index: 16 },
        { icon: <People />, primary: "Replay to all", index: 17 },
        { icon: <ForwardToInbox />, primary: "Forward", index: 18 },
        { icon: <Delete />, primary: "Delete", index: 19 }
      ]
    },
    { 
      icon: <Delete />, 
      primary: "Trash", 
      index: 20, 
      collapseItems: [
        { icon: <Restore />, primary: "Restore", index: 21 },
        { icon: <Clear />, primary: "Remove", index: 22 },
        { icon: <DeleteForever />, primary: "Remove All", index: 23 },
      ]
    }
  ];

  export default navigationData;