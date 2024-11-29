import React, { useEffect, useState } from "react";
import { Box, Paper, Typography } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../../../store";
import "../../../../style/ListOfClientsCompany.css";
import { ThemeProvider } from "@emotion/react";
import ListOfClientsTheme from "../../../../themes/ListOfClientsCompanyTheme";
import { clientCompanyImages } from "./helperfunctions/clientCompanyImages";
import { filterData } from "../../../email/emailCreator/findUserOrClientEmail/helperFunctions/filterData";

type FilteredEntity = {
    name: string;
    image: string;
};

const ListOfClientsCompany = () => {
    const [client, setClient] = useState<{name: string, image: string}[]>([]);
    const [company, setCompany] = useState<{name: string, image: string}[]>([]);
    const clientCompanydata = useSelector((state: RootState) => state.clientView.clientsData);
    const typeOfView = useSelector((state: RootState) => state.clientView.viewType);
    const searchValue = useSelector((state: RootState) => state.clientView.searchValue);

    const initializeData = () => {
        const images = clientCompanyImages[0].clientImage.map(img => Object.values(img)[0]);

        const clientsData = clientCompanydata.map((c, index) => {
            const image = images[index % images.length];
            return { name: `${c.name} ${c.surname}`, image };
        });

        const imagePath = Object.values(clientCompanyImages[0].companyImage[0])[0];
        const companyData = clientCompanydata
            .map(com => {
                if (!com.company) return null;
                return { name: com.company.name, image: imagePath };
            })
            .filter((entry): entry is { name: string; image: string } => entry !== null);

        setClient(clientsData);
        setCompany(companyData);
    };

    const filtredData = ({clients, companies }: { clients: FilteredEntity[], companies: FilteredEntity[]}) => {
        const filteredViewType = typeOfView === 'clients'
        ? clients.filter(client =>
            client.name.toLowerCase().includes(searchValue.toLowerCase())
        )
        : companies.filter(company =>
            company.name.toLowerCase().includes(searchValue.toLowerCase())
        );

        return filteredViewType;
    }
    
    useEffect(() => {
        initializeData();
    }, [clientCompanydata, typeOfView, searchValue]);

    const filteredView = filtredData({ clients: client, companies: company });

    return (
        <Box
            sx={{
                display: "grid",                 
                gridTemplateColumns: "repeat(3, 1fr)",  
                mt: '15px',                    
                width: "100%",                        
            }}
        >
            
                {filteredView.map((entity, index) => (
                    <ThemeProvider theme={ListOfClientsTheme} key={index}>
                        <Paper>
                            <Box
                                className="listOfClientsImage"
                                sx={{
                                    backgroundImage: `url(${entity.image})`,
                                    width: "80px",
                                    height: "80px",
                                    backgroundSize: "cover",
                                    backgroundPosition: "center",
                                    borderRadius: "8px",
                                }}
                            />
                            <Typography sx={{ ml: "20px" }}>{entity.name}</Typography>
                        </Paper>
                    </ThemeProvider>
                ))}
        </Box>
    );
};

export default ListOfClientsCompany;