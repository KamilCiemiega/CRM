import React, { useEffect, useState } from "react";
import { Box, Paper, Typography } from "@mui/material";
import image from "../../../../images/2.png";
import { useSelector } from "react-redux";
import { RootState } from "../../../store";
import "../../../../style/ListOfClientsCompany.css";
import { ThemeProvider } from "@emotion/react";
import ListOfClientsTheme from "../../../../themes/ListOfClientsCompanyTheme";

const ListOfClientsCompany = () => {
    const [client, setClient] = useState<string[]>([]);
    const [company, setCompany] = useState<string[]>([]);
    const clientCompanydata = useSelector((state: RootState) => state.clientView.clientsData);
    const typeOfView = useSelector((state: RootState) => state.clientView.viewType);

    const initializeClientData = () => {
        const clientData = clientCompanydata.map(c => `${c.name} ${c.surname}`);
        setClient(clientData);
    };

    const initializeCompanyData = () => {
        const companyData = clientCompanydata
            .map(com => com.company?.name)
            .filter((name): name is string => !!name);
        setCompany(companyData);
    };

    useEffect(() => {
        initializeClientData();
        initializeCompanyData();
    }, []);

    return (
        <Box
            sx={{
                display: "grid",                 
                gridTemplateColumns: "repeat(3, 1fr)", 
                gap: "16px",                     
                width: "100%",                        
            }}
        >{typeOfView === "clients" && client.length === 0 && (
            <Typography>No clients available to display</Typography>
        )}
            {typeOfView === "clients" &&
                client.map((client, index) => (
                    <ThemeProvider theme={ListOfClientsTheme} key={index}>
                        <Paper>
                            <Box
                                className="listOfClientsImage"
                                sx={{
                                    backgroundImage: `url(${image})`,
                                    width: "80px",
                                    height: "80px",
                                    backgroundSize: "cover",
                                    backgroundPosition: "center",
                                    borderRadius: "8px",
                                }}
                            />
                            <Typography sx={{ ml: "20px" }}>{client}</Typography>
                        </Paper>
                    </ThemeProvider>
                ))}
            {typeOfView === "companies" &&
                company.map((company, index) => (
                    <ThemeProvider theme={ListOfClientsTheme} key={index}>
                        <Paper>
                            <Box
                                className="listOfClientsImage"
                                sx={{
                                    backgroundImage: `url(${image})`,
                                    width: "80px",
                                    height: "80px",
                                    backgroundSize: "cover",
                                    backgroundPosition: "center",
                                    borderRadius: "8px",
                                }}
                            />
                            <Typography sx={{ ml: "20px" }}>{company}</Typography>
                        </Paper>
                    </ThemeProvider>
                ))}
        </Box>
    );
};

export default ListOfClientsCompany;