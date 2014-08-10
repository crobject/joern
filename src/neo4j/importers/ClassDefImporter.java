package neo4j.importers;

import neo4j.EdgeTypes;
import neo4j.batchInserter.Neo4JBatchInserter;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.RelationshipType;

import databaseNodes.ClassDefDatabaseNode;
import databaseNodes.FileDatabaseNode;
import astnodes.ASTNode;
import astnodes.declarations.ClassDefStatement;

public class ClassDefImporter extends ASTNodeImporter
{

	public void addToDatabaseSafe(ASTNode node)
	{
		try
		{
			ClassDefDatabaseNode classDefNode = new ClassDefDatabaseNode();
			classDefNode.initialize(node);
			addClassDefToDatabase(classDefNode);

			linkClassDefToFileNode(classDefNode, curFile);
		}
		catch (RuntimeException ex)
		{
			ex.printStackTrace();
			System.err.println("Error adding class to database: "
					+ ((ClassDefStatement) node).name.getEscapedCodeStr());
			return;
		}

	}

	private void linkClassDefToFileNode(ClassDefDatabaseNode classDefNode,
			FileDatabaseNode fileNode)
	{
		RelationshipType rel = DynamicRelationshipType
				.withName(EdgeTypes.IS_FILE_OF);

		long fileId = fileNode.getId();
		long functionId = nodeStore.getIdForObject(classDefNode);

		Neo4JBatchInserter.addRelationship(fileId, functionId, rel, null);
	}

	private void addClassDefToDatabase(ClassDefDatabaseNode classDefNode)
	{
		addMainNode(classDefNode);

	}

}