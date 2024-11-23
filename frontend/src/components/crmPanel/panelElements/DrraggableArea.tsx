import React from "react";
import Draggable from "react-draggable";
import { Box, Paper, Typography } from "@mui/material";
import image from "../../../images/1.jpg";
import { Badge, BeachAccess, SupervisedUserCircle, AddIcCall } from "@mui/icons-material";

const DraggableArea = () => {
    const draggableItems = [
        { id: 1, content: "Clients section", defaultX: 50, defaultY: 30, icon: <AddIcCall /> },
        { id: 2, content: "User center", defaultX: 350, defaultY: 30, icon: <SupervisedUserCircle /> },
        { id: 3, content: "Employee section", defaultX: 650, defaultY: 30, icon: <Badge /> },
        { id: 4, content: "Vacation section", defaultX: 950, defaultY: 30, icon: <BeachAccess /> },
    ];

    return (
        <Box
            sx={{
                width: "100%",
                height: "100%",
                position: "relative",
                overflow: "hidden",
                backgroundImage: `url(${image})`,
                backgroundSize: "cover",
                backgroundPosition: "center",
                backgroundRepeat: "no-repeat",
            }}
        >
            {draggableItems.map((item) => (
                <Draggable
                    key={item.id}
                    bounds="parent"
                    defaultPosition={{ x: item.defaultX, y: item.defaultY }}
                >
                    <Paper
                        elevation={3}
                        sx={{
                            width: 250,
                            height: 300,
                            display: "flex",
                            alignItems: "center",
                            justifyContent: "center",
                            position: "absolute",
                            cursor: "move",
                            padding: "10px",
                            backgroundColor: "rgba(255,255,255,0.7)",
                            transition: "background-color 0.3s ease",
                            "&:hover": {
                                backgroundColor: "rgba(200,200,255,0.9)",
                            },
                        }}
                    >
                        {item.icon}
                        <Typography sx={{ fontSize: "12px", ml: 2 }}>
                            {item.content}
                        </Typography>
                    </Paper>
                </Draggable>
            ))}
        </Box>
    );
};

export default DraggableArea;
