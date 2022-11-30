package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AgentImportDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;
import softuni.exam.util.Messages;
import softuni.exam.util.PathFiles;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AgentServiceImpl implements AgentService {

    private final Gson gson;
    private final Validator validator;
    private final ModelMapper mapper;

    private final AgentRepository agentRepository;
    private final TownRepository townRepository;

    public AgentServiceImpl(Gson gson, Validator validator, ModelMapper mapper,
                            AgentRepository agentRepository, TownRepository townRepository) {
        this.gson = gson;
        this.validator = validator;
        this.mapper = mapper;
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return agentRepository.count()>0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(PathFiles.AGENTS_PATH);
    }

    @Override
    public String importAgents() throws IOException {
        final String json = this.readAgentsFromFile();

        final AgentImportDto[] importAgents = this.gson.fromJson(json, AgentImportDto[].class);

        final List<String> result = new ArrayList<>();

        for (AgentImportDto importAgent : importAgents) {

            final Set<ConstraintViolation<AgentImportDto>> validationErrors =
                    this.validator.validate(importAgent);

            if (validationErrors.isEmpty()) {

                final Optional<Agent> agentExistByName = this.agentRepository.findByFirstName(importAgent.getFirstName());
                final Optional<Agent> agentExistByEmail = this.agentRepository.findByEmail(importAgent.getEmail());
                final Optional<Town> town = this.townRepository.findByTownName(importAgent.getTownName());

                boolean canAdded = agentExistByName.isEmpty() && agentExistByEmail.isEmpty() && town.isPresent();

                if(canAdded) {

                    Agent agent = this.mapper.map(importAgent, Agent.class);

                    agent.setTown(town.get());

                    this.agentRepository.save(agent);

                    final String msg = Messages.SUCCESSFULLY + Messages.AGENT + Messages.DASH + agent;

                    result.add(msg);

                } else {
                    result.add(Messages.INVALID + Messages.AGENT);
                }

            } else {
                result.add(Messages.INVALID + Messages.AGENT);
            }
        }
        return String.join(System.lineSeparator(), result);
    }
    }

